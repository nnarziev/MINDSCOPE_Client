package kr.ac.inha.stress_sensor.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import kr.ac.inha.stress_sensor.DbMgr;
import kr.ac.inha.stress_sensor.Tools;
import kr.ac.inha.stress_sensor.services.LocationService;

public class ActivityRecognitionReceiver extends BroadcastReceiver {

    public static final String TAG = "ActivityRecog";
    static boolean isDynamicActivity = false;
    static boolean isStill = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Intent locationServiceIntent = new Intent(context, LocationService.class);
            if (ActivityRecognitionResult.hasResult(intent)) {
                ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

                DetectedActivity detectedActivity = result.getMostProbableActivity();
                String activity;

                switch (detectedActivity.getType()) {
                    case DetectedActivity.STILL:
                        activity = "STILL";
                        break;
                    case DetectedActivity.WALKING:
                        activity = "WALKING";
                        break;
                    case DetectedActivity.RUNNING:
                        activity = "RUNNING";
                        break;
                    case DetectedActivity.ON_BICYCLE:
                        activity = "ON_BICYCLE";
                        break;
                    case DetectedActivity.IN_VEHICLE:
                        activity = "IN_VEHICLE";
                        break;
                    case DetectedActivity.ON_FOOT:
                        activity = "ON_FOOT";
                        break;
                    case DetectedActivity.TILTING:
                        activity = "TILTING";
                        break;
                    case DetectedActivity.UNKNOWN:
                        activity = "UNKNOWN";
                        break;
                    default:
                        activity = "N/A";
                        break;
                }
                float confidence = ((float) detectedActivity.getConfidence()) / 100;

                SharedPreferences prefs = context.getSharedPreferences("Configurations", Context.MODE_PRIVATE);
                int dataSourceId = prefs.getInt("ACTIVITY_RECOGNITION", -1);
                assert dataSourceId != -1;
                DbMgr.saveMixedData(dataSourceId, result.getTime(), confidence, activity, result.getTime(), confidence);

                if (detectedActivity.getType() == DetectedActivity.STILL) {
                    isDynamicActivity = false;
                    if (confidence < 0.5)
                        isStill = false;
                } else {
                    isStill = false;
                    if (confidence < 0.5)
                        isDynamicActivity = false;
                }

                if (isDynamicActivity) { //if two consecutive dynamic activities with confidences of more than 0.5
                    Log.e(TAG, "Two consecutive dynamic activities");
                    if (!Tools.isLocationServiceRunning(context))
                        context.startService(locationServiceIntent);
                } else if (isStill) { //if two consecutive still states with confidences of more than 0.5
                    Log.e(TAG, "Two consecutive stills");
                    if (Tools.isLocationServiceRunning(context))
                        context.stopService(locationServiceIntent);
                }

                if (detectedActivity.getType() != DetectedActivity.STILL && confidence > 0.5) {
                    isDynamicActivity = true;
                } else if (detectedActivity.getType() == DetectedActivity.STILL && confidence > 0.5) {
                    isStill = true;
                }
            }
        }
    }
}
