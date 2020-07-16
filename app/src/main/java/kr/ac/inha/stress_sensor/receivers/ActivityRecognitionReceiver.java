package kr.ac.inha.stress_sensor.receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;


import kr.ac.inha.stress_sensor.R;
import kr.ac.inha.stress_sensor.Tools;
import kr.ac.inha.stress_sensor.services.LocationService;

public class ActivityRecognitionReceiver extends BroadcastReceiver {

    public static final String TAG = "ActivityRecog";
    static boolean isStill = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Intent locationServiceIntent = new Intent(context, LocationService.class);
            if (ActivityRecognitionResult.hasResult(intent)) {
                ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

                DetectedActivity detectedActivity = result.getMostProbableActivity();
                float confidence = ((float) detectedActivity.getConfidence()) / 100;
                Log.e(TAG, String.format("Activity: %s; confidence: %f", detectedActivity.getType(), confidence));

                //start the GPS location collection service after non-STILL activity with confidence of more than 0.95
                if (detectedActivity.getType() != DetectedActivity.STILL && confidence > 0.95) {
                    if (!Tools.isLocationServiceRunning(context)) {
                        context.startService(locationServiceIntent);
                    }
                    isStill = false;
                } else if (detectedActivity.getType() == DetectedActivity.STILL) {
                    if (confidence < 0.9)
                        isStill = false;
                    else {
                        // stop the GPS location collection service after two consecutive STILL activities with confidence of more than 0.9
                        if (isStill) {
                            if (Tools.isLocationServiceRunning(context)) {
                                context.stopService(locationServiceIntent);
                            }
                        }
                        isStill = true;
                    }
                }
            }
        }
    }

    @SuppressWarnings("unused")
    private void sendNotification(Context con, String content, int notifID) {
        final NotificationManager notificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "geofence_notifs";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(con.getApplicationContext(), channelId);
        builder.setContentTitle(con.getString(R.string.app_name))
                .setContentText(content)
                .setTicker("New Message Alert!")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_no_bg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, con.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        final Notification notification = builder.build();
        notificationManager.notify(notifID, notification);
    }
}
