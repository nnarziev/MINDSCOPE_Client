package kr.ac.inha.stress_sensor.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import java.util.Objects;

import kr.ac.inha.stress_sensor.Tools;

public class ConnectionReceiver extends BroadcastReceiver {
    public static final String TAG = "ConnectionReceiver";

    @Override
    public void onReceive(Context con, Intent intent) {

        if (Objects.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (Tools.isNetworkAvailable(con)) {
                try {
                    Log.d(TAG, "Network is connected");
                    //TODO: do smth when internet is connected
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(TAG, "Network is changed or reconnected");
            }
        }
    }
}
