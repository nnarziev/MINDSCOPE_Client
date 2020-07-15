package kr.ac.inha.stress_sensor.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import android.util.Log;
import android.util.SparseIntArray;


import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import inha.nsl.easytrack.ETServiceGrpc;
import inha.nsl.easytrack.EtService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import kr.ac.inha.stress_sensor.AppUseDb;
import kr.ac.inha.stress_sensor.AuthenticationActivity;
import kr.ac.inha.stress_sensor.DbMgr;
import kr.ac.inha.stress_sensor.EMAActivity;
import kr.ac.inha.stress_sensor.MainActivity;
import kr.ac.inha.stress_sensor.R;
import kr.ac.inha.stress_sensor.receivers.ActivityTransitionsReceiver;
import kr.ac.inha.stress_sensor.Tools;
import kr.ac.inha.stress_sensor.receivers.ActivityRecognitionReceiver;
import kr.ac.inha.stress_sensor.receivers.CallReceiver;
import kr.ac.inha.stress_sensor.receivers.ScreenAndUnlockReceiver;

import static kr.ac.inha.stress_sensor.EMAActivity.EMA_NOTIF_HOURS;
import static kr.ac.inha.stress_sensor.receivers.CallReceiver.AudioRunningForCall;

public class MainService extends Service {
    private static final String TAG = "CustomSensorsService";

    //region Constants
    private static final int ID_SERVICE = 101;
    public static final int EMA_NOTIFICATION_ID = 1234; //in sec
    public static final int PERMISSION_REQUEST_NOTIFICATION_ID = 1111; //in sec
    public static final long EMA_RESPONSE_EXPIRE_TIME = 3600;  //in sec
    public static final int SERVICE_START_X_MIN_BEFORE_EMA = (EMA_NOTIF_HOURS[1] - EMA_NOTIF_HOURS[0]) * 60 * 60; //in sec
    public static final short HEARTBEAT_PERIOD = 30;  //in sec
    public static final short DATA_SUBMIT_PERIOD = 5 * 60;  //in sec
    private static final short AUDIO_RECORDING_PERIOD = 20 * 60;  //in sec
    private static final short AUDIO_RECORDING_DURATION = 5;  //in sec
    private static final int ACTIVITY_RECOGNITION_INTERVAL = 40; //in sec
    private static final int APP_USAGE_SAVE_PERIOD = 3; //in sec
    private static final int APP_USAGE_SUBMIT_PERIOD = 5 * 60; //in sec
    //endregion

    public static HashMap<String, Integer> sensorNameToTypeMap;
    public static SparseIntArray sensorToDataSourceIdMap;

    static SharedPreferences loginPrefs;

    private long prevAudioRecordStartTime = 0;

    //private StationaryDetector mStationaryDetector;
    static NotificationManager mNotificationManager;
    static Boolean permissionNotificationPosted;
    private SensorManager mSensorManager;

    private ScreenAndUnlockReceiver mPhoneUnlockedReceiver;
    private CallReceiver mCallReceiver;

    private AudioFeatureRecorder audioFeatureRecorder;

    private ActivityRecognitionClient activityRecognitionClient;
    private PendingIntent activityRecPendingIntent;

    private ActivityRecognitionClient activityTransitionClient;
    private PendingIntent activityTransPendingIntent;

    private boolean canSendNotif = true;

    private Handler mainHandler = new Handler();
    private Runnable mainRunnable = new Runnable() {
        @Override
        public void run() {

            //check if all permissions are set then dismiss notification for request
            if (Tools.hasPermissions(getApplicationContext(), Tools.PERMISSIONS)) {
                mNotificationManager.cancel(PERMISSION_REQUEST_NOTIFICATION_ID);
                permissionNotificationPosted = false;
            }

            //permissions granted or not. If not grant first
            if (!Tools.hasPermissions(getApplicationContext(), Tools.PERMISSIONS) && !permissionNotificationPosted) {
                permissionNotificationPosted = true;
                sendNotificationForPermissionSetting();
            }

            long curTimestamp = System.currentTimeMillis();
            Calendar curCal = Calendar.getInstance();

            //region Sending Notification and some statistics periodically
            int ema_order = Tools.getEMAOrderAtExactTime(curCal);
            if (ema_order != 0 && canSendNotif) {
                Log.e(TAG, "EMA order 1: " + ema_order);
                sendNotification(ema_order);
                loginPrefs = getSharedPreferences("UserLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginPrefs.edit();
                editor.putBoolean("ema_btn_make_visible", true);
                editor.apply();
                canSendNotif = false;
                //saveSomeStats(); //save some stats that we need only once per EMA is posted
            }

            if (curCal.get(Calendar.MINUTE) > 0)
                canSendNotif = true;
            //endregion

            //region Registering Audio recorder periodically
            boolean canStartAudioRecord = (curTimestamp > prevAudioRecordStartTime + AUDIO_RECORDING_PERIOD * 1000) || AudioRunningForCall;
            boolean stopAudioRecord = (curTimestamp > prevAudioRecordStartTime + AUDIO_RECORDING_DURATION * 1000);
            if (canStartAudioRecord) {
                if (audioFeatureRecorder == null)
                    audioFeatureRecorder = new AudioFeatureRecorder(MainService.this);
                audioFeatureRecorder.start();
                prevAudioRecordStartTime = curTimestamp;
            } else if (stopAudioRecord) {
                if (audioFeatureRecorder != null) {
                    audioFeatureRecorder.stop();
                    audioFeatureRecorder = null;
                }
            }
            //endregion

            mainHandler.postDelayed(this, 5 * 1000);
        }
    };

    private Handler dataSubmissionHandler = new Handler();
    private Runnable dataSubmitRunnable = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (Tools.isNetworkAvailable()) {
                        Cursor cursor = DbMgr.getSensorData();
                        if (cursor.moveToFirst()) {
                            ManagedChannel channel = ManagedChannelBuilder.forAddress(
                                    getString(R.string.grpc_host),
                                    Integer.parseInt(getString(R.string.grpc_port))
                            ).usePlaintext().build();
                            ETServiceGrpc.ETServiceBlockingStub stub = ETServiceGrpc.newBlockingStub(channel);

                            loginPrefs = getSharedPreferences("UserLogin", MODE_PRIVATE);
                            int userId = loginPrefs.getInt(AuthenticationActivity.user_id, -1);
                            String email = loginPrefs.getString(AuthenticationActivity.usrEmail, null);

                            try {
                                do {
                                    EtService.SubmitDataRecordRequestMessage submitDataRecordRequestMessage = EtService.SubmitDataRecordRequestMessage.newBuilder()
                                            .setUserId(userId)
                                            .setEmail(email)
                                            .setDataSource(cursor.getInt(1))
                                            .setTimestamp(cursor.getLong(2))
                                            .setValues(cursor.getString(4))
                                            .build();
                                    //String res = cursor.getInt(0) + ", " + cursor.getLong(1) + ", " + cursor.getLong(2) + ", " + cursor.getLong(4);
                                    //Log.e("submitThread", "Submission: " + res);
                                    EtService.DefaultResponseMessage responseMessage = stub.submitDataRecord(submitDataRecordRequestMessage);

                                    if (responseMessage.getDoneSuccessfully()) {
                                        DbMgr.deleteRecord(cursor.getInt(0));
                                    }

                                } while (cursor.moveToNext());
                            } catch (StatusRuntimeException e) {
                                Log.e(TAG, "DataCollectorService.setUpDataSubmissionThread() exception: " + e.getMessage());
                                e.printStackTrace();
                            } finally {
                                channel.shutdown();
                            }
                        }
                        cursor.close();
                    }

                }
            }).start();
            dataSubmissionHandler.postDelayed(dataSubmitRunnable, DATA_SUBMIT_PERIOD * 60 * 1000);
        }
    };

    private Handler appUsageSubmitHandler = new Handler();
    private Runnable appUsageSubmitRunnable = new Runnable() {
        //TODO: test how it works
        @Override
        public void run() {
            final long app_usage_time_end = System.currentTimeMillis();
            final long app_usage_time_start = (app_usage_time_end - APP_USAGE_SUBMIT_PERIOD * 1000) + 1000; // add one second to start time
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences configPrefs = getSharedPreferences("Configurations", Context.MODE_PRIVATE);
                    int dataSourceId = configPrefs.getInt("APPLICATION_USAGE", -1);
                    assert dataSourceId != -1;
                    Cursor cursor = AppUseDb.getAppUsage();
                    if (cursor.moveToFirst()) {
                        do {
                            String package_name = cursor.getString(1);
                            long start_time = cursor.getLong(2);
                            long end_time = cursor.getLong(3);
                            if (Tools.inRange(start_time, app_usage_time_start, app_usage_time_end) && Tools.inRange(end_time, app_usage_time_start, app_usage_time_end))
                                if (start_time < end_time) {
                                    //Log.e(TAG, "Inserting -> package: " + package_name + "; start: " + start_time + "; end: " + end_time);
                                    DbMgr.saveMixedData(dataSourceId, start_time, 1.0f, start_time, end_time, package_name);
                                }
                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close();
                }
            }).start();
            appUsageSubmitHandler.postDelayed(appUsageSubmitRunnable, APP_USAGE_SUBMIT_PERIOD * 1000);
        }
    };

    private Handler appUsageSaveHandler = new Handler();
    private Runnable appUsageSaveRunnable = new Runnable() {
        public void run() {
            try {
                Tools.checkAndSendUsageAccessStats(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            appUsageSaveHandler.postDelayed(this, APP_USAGE_SAVE_PERIOD * 1000);
        }
    };

    private Handler heartBeatHandler = new Handler();
    private Runnable heartBeatSendRunnable = new Runnable() {
        public void run() {
            try {
                if (Tools.heartbeatNotSent(MainService.this)) {
                    Log.e(TAG, "Heartbeat not sent");
                    /*Tools.perform_logout(CustomSensorsService.this);
                    stopSelf();*/
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            heartBeatHandler.postDelayed(this, HEARTBEAT_PERIOD * 1000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        loginPrefs = getSharedPreferences("UserLogin", MODE_PRIVATE);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorNameToTypeMap = new HashMap<>();
        sensorToDataSourceIdMap = new SparseIntArray();
        initDataSourceNameIdMap();
        setUpNewDataSources();

        activityRecognitionClient = ActivityRecognition.getClient(getApplicationContext());
        activityRecPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2, new Intent(getApplicationContext(), ActivityRecognitionReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        activityRecognitionClient.requestActivityUpdates(ACTIVITY_RECOGNITION_INTERVAL * 1000, activityRecPendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Registered: Activity Recognition");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failed: Activity Recognition");
                    }
                });

        activityTransitionClient = ActivityRecognition.getClient(getApplicationContext());
        activityTransPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(getApplicationContext(), ActivityTransitionsReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        activityTransitionClient.requestActivityTransitionUpdates(new ActivityTransitionRequest(getActivityTransitions()), activityTransPendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Registered: Activity Transition");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failed: Activity Transition " + e.toString());
                    }
                });

        //region Register Phone unlock and Screen On state receiver
        mPhoneUnlockedReceiver = new ScreenAndUnlockReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mPhoneUnlockedReceiver, filter);
        //endregion

        //region Register Phone call logs receiver
        mCallReceiver = new CallReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        intentFilter.addAction(Intent.EXTRA_PHONE_NUMBER);
        registerReceiver(mCallReceiver, intentFilter);
        //endregion

        //region Posting Foreground notification when service is started
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channel_id = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel() : "";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setSmallIcon(R.mipmap.ic_launcher_no_bg)
                .setCategory(NotificationCompat.CATEGORY_SERVICE);
        Notification notification = builder.build();
        startForeground(ID_SERVICE, notification);
        //endregion

        mainHandler.post(mainRunnable);
        heartBeatHandler.post(heartBeatSendRunnable);
        appUsageSaveHandler.post(appUsageSaveRunnable);
        appUsageSubmitHandler.post(appUsageSubmitRunnable);
        dataSubmissionHandler.post(dataSubmitRunnable);

        permissionNotificationPosted = false;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public String createNotificationChannel() {
        String id = "YouNoOne_channel_id";
        String name = "You no one channel id";
        String description = "This is description";
        NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
        }
        return id;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //region Unregister listeners
        activityRecognitionClient.removeActivityUpdates(activityRecPendingIntent);
        activityTransitionClient.removeActivityTransitionUpdates(activityTransPendingIntent);
        if (audioFeatureRecorder != null)
            audioFeatureRecorder.stop();
        unregisterReceiver(mPhoneUnlockedReceiver);
        unregisterReceiver(mCallReceiver);
        mainHandler.removeCallbacks(mainRunnable);
        heartBeatHandler.removeCallbacks(heartBeatSendRunnable);
        appUsageSaveHandler.removeCallbacks(appUsageSaveRunnable);
        dataSubmissionHandler.removeCallbacks(dataSubmitRunnable);
        appUsageSubmitHandler.removeCallbacks(appUsageSubmitRunnable);
        //endregion

        //region Stop foreground service
        stopForeground(false);
        mNotificationManager.cancel(ID_SERVICE);
        mNotificationManager.cancel(PERMISSION_REQUEST_NOTIFICATION_ID);
        //endregion

        Tools.sleep(1000);

        super.onDestroy();
    }

    public List<ActivityTransition> getActivityTransitions() {
        List<ActivityTransition> transitionList = new ArrayList<>();
        ArrayList<Integer> activities = new ArrayList<>(Arrays.asList(
                DetectedActivity.STILL,
                DetectedActivity.WALKING,
                DetectedActivity.RUNNING,
                DetectedActivity.ON_BICYCLE,
                DetectedActivity.IN_VEHICLE));
        for (int activity : activities) {
            transitionList.add(new ActivityTransition.Builder()
                    .setActivityType(activity)
                    .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER).build());

            transitionList.add(new ActivityTransition.Builder()
                    .setActivityType(activity)
                    .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT).build());
        }

        return transitionList;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initDataSourceNameIdMap() {
        sensorNameToTypeMap.put("ANDROID_ACCELEROMETER", Sensor.TYPE_ACCELEROMETER);
        sensorNameToTypeMap.put("ANDROID_AMBIENT_TEMPERATURE", Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorNameToTypeMap.put("ANDROID_GRAVITY", Sensor.TYPE_GRAVITY);
        sensorNameToTypeMap.put("ANDROID_GYROSCOPE", Sensor.TYPE_GYROSCOPE);
        sensorNameToTypeMap.put("ANDROID_LIGHT", Sensor.TYPE_LIGHT);
        sensorNameToTypeMap.put("ANDROID_LINEAR_ACCELERATION", Sensor.TYPE_LINEAR_ACCELERATION);
        sensorNameToTypeMap.put("ANDROID_MAGNETIC_FIELD", Sensor.TYPE_MAGNETIC_FIELD);
        sensorNameToTypeMap.put("ANDROID_PRESSURE", Sensor.TYPE_PRESSURE);
        sensorNameToTypeMap.put("ANDROID_PROXIMITY", Sensor.TYPE_PROXIMITY);
        sensorNameToTypeMap.put("ANDROID_RELATIVE_HUMIDITY", Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorNameToTypeMap.put("ANDROID_ROTATION_VECTOR", Sensor.TYPE_ROTATION_VECTOR);
        sensorNameToTypeMap.put("ANDROID_GAME_ROTATION_VECTOR", Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorNameToTypeMap.put("ANDROID_SIGNIFICANT_MOTION", Sensor.TYPE_SIGNIFICANT_MOTION);
        sensorNameToTypeMap.put("ANDROID_GYROSCOPE_UNCALIBRATED", Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        sensorNameToTypeMap.put("ANDROID_MAGNETIC_FIELD_UNCALIBRATED", Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        sensorNameToTypeMap.put("ANDROID_STEP_COUNTER", Sensor.TYPE_STEP_COUNTER);
        sensorNameToTypeMap.put("ANDROID_GEOMAGNETIC_ROTATION_VECTOR", Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        sensorNameToTypeMap.put("ANDROID_STEP_DETECTOR", Sensor.TYPE_STEP_DETECTOR);
        sensorNameToTypeMap.put("ANDROID_HEART_RATE", Sensor.TYPE_HEART_RATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sensorNameToTypeMap.put("ANDROID_MOTION_DETECTION", Sensor.TYPE_MOTION_DETECT);
            sensorNameToTypeMap.put("ANDROID_POSE_6DOF", Sensor.TYPE_POSE_6DOF);
            sensorNameToTypeMap.put("ANDROID_HEART_BEAT", Sensor.TYPE_HEART_BEAT);
            sensorNameToTypeMap.put("ANDROID_STATIONARY_DETECT", Sensor.TYPE_STATIONARY_DETECT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sensorNameToTypeMap.put("ANDROID_LOW_LATENCY_OFFBODY_DETECT", Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT);
                sensorNameToTypeMap.put("ANDROID_ACCELEROMETER_UNCALIBRATED", Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
            }
        }
    }

    private void setUpNewDataSources() {
        SharedPreferences prefs = getSharedPreferences("Configurations", Context.MODE_PRIVATE);
        String dataSourceNames = prefs.getString("dataSourceNames", null);
        if (dataSourceNames != null)
            for (String dataSourceName : dataSourceNames.split(",")) {
                String json = prefs.getString(String.format(Locale.getDefault(), "config_json_%s", dataSourceName), null);
                setUpDataSource(dataSourceName, json, prefs);
            }
    }

    private void setUpDataSource(String dataSourceName, String configJson, SharedPreferences prefs) {
        if (sensorNameToTypeMap.containsKey(dataSourceName)) {
            Integer sensorId = sensorNameToTypeMap.get(dataSourceName);
            assert sensorId != null;
            Sensor sensor = mSensorManager.getDefaultSensor(sensorId);
            sensorToDataSourceIdMap.put(sensorId, prefs.getInt(dataSourceName, -1));
        } else {
            // GPS, Activity Recognition, App Usage, Survey, etc.
        }
    }

    private void sendNotification(int ema_order) {
        final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(MainService.this, EMAActivity.class);
        notificationIntent.putExtra("ema_order", ema_order);
        //PendingIntent pendingIntent = PendingIntent.getActivities(CustomSensorsService.this, 0, new Intent[]{notificationIntent}, 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainService.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = this.getString(R.string.notif_channel_id);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext(), channelId);
        builder.setContentTitle(this.getString(R.string.app_name))
                .setTimeoutAfter(1000 * EMA_RESPONSE_EXPIRE_TIME)
                .setContentText(this.getString(R.string.daily_notif_text))
                .setTicker("New Message Alert!")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_no_bg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, this.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        final Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(EMA_NOTIFICATION_ID, notification);
        }
    }

    private void sendNotificationForPermissionSetting() {
        final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(MainService.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainService.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = "StressSensor_permission_notif";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext(), channelId);
        builder.setContentTitle(this.getString(R.string.app_name))
                .setContentText(this.getString(R.string.grant_permissions))
                .setTicker("New Message Alert!")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_no_bg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, this.getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        final Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(PERMISSION_REQUEST_NOTIFICATION_ID, notification);
        }
    }

    //function to save some stats when the EMA button submit it clicked
    private void saveSomeStats() {

        // saving GPS statistics every notification time
        startService(new Intent(MainService.this, SaveGPSStats.class));

        final long app_usage_time_end = System.currentTimeMillis();
        final long app_usage_time_start = (app_usage_time_end - SERVICE_START_X_MIN_BEFORE_EMA * 1000) + 1000; // add one second to start time
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences configPrefs = getSharedPreferences("Configurations", Context.MODE_PRIVATE);
                int dataSourceId = configPrefs.getInt("APPLICATION_USAGE", -1);
                assert dataSourceId != -1;
                Cursor cursor = AppUseDb.getAppUsage();
                if (cursor.moveToFirst()) {
                    do {
                        String package_name = cursor.getString(1);
                        long start_time = cursor.getLong(2);
                        long end_time = cursor.getLong(3);
                        if (Tools.inRange(start_time, app_usage_time_start, app_usage_time_end) && Tools.inRange(end_time, app_usage_time_start, app_usage_time_end))
                            if (start_time < end_time) {
                                //Log.e(TAG, "Inserting -> package: " + package_name + "; start: " + start_time + "; end: " + end_time);
                                DbMgr.saveMixedData(dataSourceId, start_time, 1.0f, start_time, end_time, package_name);
                            }
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
            }
        }).start();

    }
}
