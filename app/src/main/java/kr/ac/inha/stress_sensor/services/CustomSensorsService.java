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
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import inha.nsl.easytrack.ETServiceGrpc;
import inha.nsl.easytrack.EtService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import kr.ac.inha.stress_sensor.AuthenticationActivity;
import kr.ac.inha.stress_sensor.DbMgr;
import kr.ac.inha.stress_sensor.EMAActivity;
import kr.ac.inha.stress_sensor.R;
import kr.ac.inha.stress_sensor.receivers.ActivityTransitionsReceiver;
import kr.ac.inha.stress_sensor.Tools;
import kr.ac.inha.stress_sensor.receivers.ActivityRecognitionReceiver;
import kr.ac.inha.stress_sensor.receivers.CallReceiver;
import kr.ac.inha.stress_sensor.receivers.ScreenAndUnlockReceiver;

import static kr.ac.inha.stress_sensor.receivers.CallReceiver.AudioRunningForCall;

public class CustomSensorsService extends Service implements SensorEventListener {
    private static final String TAG = "CustomSensorsService";

    //region Constants
    private static final int ID_SERVICE = 101;
    public static final int EMA_NOTIFICATION_ID = 1234; //in sec
    public static final long EMA_RESPONSE_EXPIRE_TIME = 3600;  //in sec
    public static final int EMA_BTN_VISIBLE_X_MIN_AFTER_EMA = 60; //min
    public static final int SERVICE_START_X_MIN_BEFORE_EMA = 3 * 60; //min
    public static final short HEARTBEAT_PERIOD = 5;  //in min
    public static final short DATA_SUBMIT_PERIOD = 5;  //in min
    private static final short LIGHT_SENSOR_READ_PERIOD = 20 * 60;  //in sec
    private static final short LIGHT_SENSOR_READ_DURATION = 20;  //in sec
    private static final short AUDIO_RECORDING_PERIOD = 20 * 60;  //in sec
    private static final short AUDIO_RECORDING_DURATION = 20;  //in sec
    private static final int ACTIVITY_RECOGNITION_INTERVAL = 60; //in sec
    private static final int APP_USAGE_SEND_PERIOD = 10; //in sec


    /*public static final short DATA_SRC_ACC = 1;
    public static final short DATA_SRC_STATIONARY_DUR = 2;
    public static final short DATA_SRC_SCREEN_ON_DUR = 3;
    public static final short DATA_SRC_STEP_DETECTOR = 4;
    public static final short DATA_SRC_UNLOCKED_DUR = 5;
    public static final short DATA_SRC_PHONE_CALLS = 6;
    public static final short DATA_SRC_LIGHT = 7;
    public static final short DATA_SRC_APP_USAGE = 8;
    public static final short DATA_SRC_GPS_LOCATIONS = 9;
    public static final short DATA_SRC_ACTIVITY = 10;
    public static final short DATA_SRC_TOTAL_DIST_COVERED = 11;
    public static final short DATA_SRC_MAX_DIST_FROM_HOME = 12;
    public static final short DATA_SRC_MAX_DIST_TWO_LOCATIONS = 13;
    public static final short DATA_SRC_RADIUS_OF_GYRATION = 14;
    public static final short DATA_SRC_STDDEV_OF_DISPLACEMENT = 15;
    public static final short DATA_SRC_NUM_OF_DIF_PLACES = 16;
    public static final short DATA_SRC_AUDIO_LOUDNESS = 17;
    public static final short DATA_SRC_ACTIVITY_DURATION = 18;*/

    //endregion

    public static HashMap<String, Integer> sensorNameToTypeMap;
    public static SparseIntArray sensorToDataSourceIdMap;

    SharedPreferences loginPrefs;

    long prevLightSensorReadingTime = 0;
    long prevAudioRecordStartTime = 0;

    //private StationaryDetector mStationaryDetector;
    NotificationManager mNotificationManager;
    private SensorManager mSensorManager;
    private Sensor sensorLight;
    private Sensor sensorStepDetect;
    private Sensor sensorAcc;

    private ScreenAndUnlockReceiver mPhoneUnlockedReceiver;
    private CallReceiver mCallReceiver;

    //private AudioRecorder audioRecorder;
    private AudioFeatureRecorder audioFeatureRecorder;

    private ActivityRecognitionClient activityRecognitionClient;
    private PendingIntent activityRecPendingIntent;

    private ActivityRecognitionClient activityTransitionClient;
    private PendingIntent activityTransPendingIntent;

    ScheduledExecutorService dataSubmitScheduler = Executors.newSingleThreadScheduledExecutor();
    ScheduledExecutorService appUsageSubmitScheduler = Executors.newSingleThreadScheduledExecutor();
    ScheduledExecutorService heartbeatSendScheduler = Executors.newSingleThreadScheduledExecutor();

    private boolean canSendNotif = true;

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            long curTimestamp = System.currentTimeMillis();
            Calendar curCal = Calendar.getInstance();

            //region Sending Notification periodically
            short ema_order = Tools.getEMAOrderAtExactTime(curCal);
            if (ema_order != 0 && canSendNotif) {
                Log.e(TAG, "EMA order 1: " + ema_order);
                sendNotification(ema_order);
                SharedPreferences.Editor editor = loginPrefs.edit();
                editor.putBoolean("ema_btn_make_visible", true);
                editor.apply();
                canSendNotif = false;
            }

            if (curCal.get(Calendar.MINUTE) != 0)
                canSendNotif = true;
            //endregion

            //region Registering Light sensor periodically
            boolean canLightSense = curTimestamp > prevLightSensorReadingTime + LIGHT_SENSOR_READ_PERIOD * 1000;
            boolean stopLightSensor = curTimestamp > prevLightSensorReadingTime + LIGHT_SENSOR_READ_DURATION * 1000;
            if (canLightSense) {
                if (sensorLight == null) {
                    sensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    mSensorManager.registerListener(CustomSensorsService.this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
                    prevLightSensorReadingTime = curTimestamp;
                }
            } else if (stopLightSensor) {
                if (sensorLight != null) {
                    mSensorManager.unregisterListener(CustomSensorsService.this, sensorLight);
                    sensorLight = null;
                }
            }
            //endregion

            //region Registering Audio recorder periodically
            boolean canStartAudioRecord = (curTimestamp > prevAudioRecordStartTime + AUDIO_RECORDING_PERIOD * 1000) || AudioRunningForCall;
            boolean stopAudioRecord = (curTimestamp > prevAudioRecordStartTime + AUDIO_RECORDING_DURATION * 1000);
            if (canStartAudioRecord) {
                if (audioFeatureRecorder == null) {
                    audioFeatureRecorder = new AudioFeatureRecorder(CustomSensorsService.this);
                    audioFeatureRecorder.start();
                    prevAudioRecordStartTime = curTimestamp;
                }
            } else if (stopAudioRecord) {
                if (audioFeatureRecorder != null) {
                    audioFeatureRecorder.stop();
                    audioFeatureRecorder = null;
                }
            }
            //endregion

            mHandler.postDelayed(this, 2 * 1000);
        }
    };

    private boolean stopDataSubmitThread = false;
    private Runnable datasubmitRunnable = new Runnable() {
        @Override
        public void run() {
            Cursor cursor = DbMgr.getSensorData();
            if (cursor.moveToFirst()) {
                ManagedChannel channel = ManagedChannelBuilder.forAddress(
                        getString(R.string.grpc_host),
                        Integer.parseInt(getString(R.string.grpc_port))
                ).usePlaintext().build();
                ETServiceGrpc.ETServiceBlockingStub stub = ETServiceGrpc.newBlockingStub(channel);

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
                            //Log.e("Deletion", "Deletion: " + cursor.getInt(0));
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
    };

    private Runnable AppUsageSubmitRunnable = new Runnable() {
        public void run() {
            try {
                Tools.checkAndSendUsageAccessStats(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable HeartBeatSendRunnable = new Runnable() {
        public void run() {
            try {
                if (Tools.heartbeatNotSent(CustomSensorsService.this)) {
                    Tools.perform_logout(CustomSensorsService.this);
                    stopSelf();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        sensorAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //region Register Step detector sensor
        sensorStepDetect = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (sensorStepDetect != null) {
            mSensorManager.registerListener(this, sensorStepDetect, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.e(TAG, "Step detector sensor is NOT available");
        }
        //endregion

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

        mHandler.post(mRunnable);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopDataSubmitThread) {
                    if (Tools.isNetworkAvailable(CustomSensorsService.this))
                        datasubmitRunnable.run();
                    /*try {
                        Thread.sleep(DATA_SUBMIT_PERIOD * 60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }).start();
        heartbeatSendScheduler.scheduleAtFixedRate(HeartBeatSendRunnable, 0, HEARTBEAT_PERIOD, TimeUnit.MINUTES);
        appUsageSubmitScheduler.scheduleAtFixedRate(AppUsageSubmitRunnable, 0, APP_USAGE_SEND_PERIOD, TimeUnit.SECONDS);
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
        mNotificationManager.createNotificationChannel(mChannel);
        return id;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //region Unregister listeners
        mSensorManager.unregisterListener(this, sensorLight);
        mSensorManager.unregisterListener(this, sensorAcc);
        mSensorManager.unregisterListener(this, sensorStepDetect);
        activityRecognitionClient.removeActivityUpdates(activityRecPendingIntent);
        activityTransitionClient.removeActivityTransitionUpdates(activityTransPendingIntent);
        if (audioFeatureRecorder != null)
            audioFeatureRecorder.stop();
        unregisterReceiver(mPhoneUnlockedReceiver);
        unregisterReceiver(mCallReceiver);
        stopDataSubmitThread = true;
        mHandler.removeCallbacks(mRunnable);
        //endregion

        //region Stop foreground service
        stopForeground(false);
        mNotificationManager.cancel(ID_SERVICE);
        //endregion

        Tools.sleep(1000);

        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int dataSourceId = sensorToDataSourceIdMap.get(event.sensor.getType());
        /*if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int dataSourceId = CustomSensorsService.customDataNameToSourceIdMap.get("DATA_SRC_ACC");
            DbMgr.saveMixedData(dataSourceId, System.currentTimeMillis(), 1.0f, System.currentTimeMillis(), event.values[0], event.values[1], event.values[2]);
        } else*/
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            DbMgr.saveMixedData(dataSourceId, System.currentTimeMillis(), 1.0f, System.currentTimeMillis());
        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            DbMgr.saveMixedData(dataSourceId, System.currentTimeMillis(), 1.0f, System.currentTimeMillis(), event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
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
        sensorNameToTypeMap.put("ANDROID_ORIENTATION", Sensor.TYPE_ORIENTATION);
        sensorNameToTypeMap.put("ANDROID_PRESSURE", Sensor.TYPE_PRESSURE);
        sensorNameToTypeMap.put("ANDROID_PROXIMITY", Sensor.TYPE_PROXIMITY);
        sensorNameToTypeMap.put("ANDROID_RELATIVE_HUMIDITY", Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorNameToTypeMap.put("ANDROID_ROTATION_VECTOR", Sensor.TYPE_ROTATION_VECTOR);
        sensorNameToTypeMap.put("ANDROID_TEMPERATURE", Sensor.TYPE_TEMPERATURE);
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


    private void sendNotification(short ema_order) {
        final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(CustomSensorsService.this, EMAActivity.class);
        Log.e(TAG, "EMA order 2: " + ema_order);
        notificationIntent.putExtra("ema_order", ema_order);
        //PendingIntent pendingIntent = PendingIntent.getActivities(CustomSensorsService.this, 0, new Intent[]{notificationIntent}, 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(CustomSensorsService.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

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
            notificationManager.createNotificationChannel(channel);
        }

        final Notification notification = builder.build();
        notificationManager.notify(EMA_NOTIFICATION_ID, notification);
    }
}