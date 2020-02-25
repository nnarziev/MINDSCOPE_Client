package kr.ac.inha.stress_sensor.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.Calendar;
import java.util.List;

import kr.ac.inha.stress_sensor.DbMgr;

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
                SharedPreferences.Editor editor = locationPrefs.edit();
                editor.putLong(geofence.getRequestId() + "_ENTERED_TIME", curTime.getTimeInMillis());
                editor.apply();
            }
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Calendar curTime = Calendar.getInstance();
            for (Geofence geofence : triggeringGeofences) {
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
}
