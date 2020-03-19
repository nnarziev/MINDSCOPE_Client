package kr.ac.inha.stress_sensor.receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.Calendar;
import java.util.List;

import kr.ac.inha.stress_sensor.DbMgr;
import kr.ac.inha.stress_sensor.R;

import static android.content.Context.MODE_PRIVATE;

public class GeofenceReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences locationPrefs = context.getSharedPreferences("UserLocations", MODE_PRIVATE);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            String error = String.valueOf(geofencingEvent.getErrorCode());
            Log.e(TAG, "Error code: " + error);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Calendar curTime = Calendar.getInstance();
            for (Geofence geofence : triggeringGeofences) {
                //sendNotification(context, "GEO EN: " + geofence.getRequestId(), true);
                SharedPreferences.Editor editor = locationPrefs.edit();
                editor.putLong(geofence.getRequestId() + "_ENTERED_TIME", curTime.getTimeInMillis());
                editor.apply();
            }
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Calendar curTime = Calendar.getInstance();
            for (Geofence geofence : triggeringGeofences) {
                //sendNotification(context, "Geo EX: " + geofence.getRequestId(), false);
                long entered_time = locationPrefs.getLong(geofence.getRequestId() + "_ENTERED_TIME", 0);
                long exited_time = curTime.getTimeInMillis();
                SharedPreferences prefs = context.getSharedPreferences("Configurations", Context.MODE_PRIVATE);
                int dataSourceId = prefs.getInt("GEOFENCE", -1);
                assert dataSourceId != -1;
                DbMgr.saveMixedData(dataSourceId, entered_time, 1.0f, entered_time, exited_time, geofence.getRequestId());
                SharedPreferences.Editor editor = locationPrefs.edit();
                editor.remove(geofence.getRequestId() + "_ENTERED_TIME");
                editor.apply();
            }
        } else {
            // Log the error.
            Log.e(TAG, "geofence transition type error: " + geofenceTransition);
        }
    }

    private void sendNotification(Context con, String content, boolean isEntered) {
        final NotificationManager notificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificaiton_id = 1234;  //notif id for exit
        if (isEntered) {
            notificaiton_id = 4567;  //notif id for enter
        }

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
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        final Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(notificaiton_id, notification);
        }
    }
}
