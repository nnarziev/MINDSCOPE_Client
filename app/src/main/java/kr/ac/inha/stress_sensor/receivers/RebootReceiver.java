package kr.ac.inha.stress_sensor.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

import kr.ac.inha.stress_sensor.AuthenticationActivity;

public class RebootReceiver extends BroadcastReceiver {
    public static final String TAG = "RebootReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Objects.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED)){
            Intent intentService = new Intent(context, AuthenticationActivity.class);
            intentService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentService.putExtra("fromReboot", true);
            context.startActivity(intentService);
        }
        else {
            Log.e(TAG, "Received unexpected intent " + intent.toString());
        }
    }
}
