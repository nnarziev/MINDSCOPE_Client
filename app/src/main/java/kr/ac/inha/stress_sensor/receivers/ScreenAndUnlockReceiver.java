package kr.ac.inha.stress_sensor.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Objects;

import kr.ac.inha.stress_sensor.DbMgr;


public class ScreenAndUnlockReceiver extends BroadcastReceiver {
    public static final String TAG = "ScreenAndUnlockReceiver";

    private long phoneUnlockedDurationStart = System.currentTimeMillis();
    private long screenONStartTime = System.currentTimeMillis();
    private boolean unlocked = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), Intent.ACTION_USER_PRESENT)) {
            Log.e(TAG, "Phone unlocked");
            unlocked = true;
            phoneUnlockedDurationStart = System.currentTimeMillis();
        } else if (Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_OFF)) {
            Log.e(TAG, "Phone locked / Screen OFF");
            //region Handling phone locked state
            if (unlocked) {
                unlocked = false;
                long phoneUnlockedDurationEnd = System.currentTimeMillis();
                long phoneUnlockedDuration = (phoneUnlockedDurationEnd - phoneUnlockedDurationStart) / 1000; // in seconds
                SharedPreferences prefs = context.getSharedPreferences("Configurations", Context.MODE_PRIVATE);
                int dataSourceId = prefs.getInt("UNLOCK_DURATION", -1);
                assert dataSourceId != -1;
                DbMgr.saveMixedData(dataSourceId, phoneUnlockedDurationStart, 1.0f, phoneUnlockedDurationStart, phoneUnlockedDurationEnd, phoneUnlockedDuration);
            }
            //endregion

            //region Handling screen OFF state
            long screenONEndTime = System.currentTimeMillis();
            long screenOnDuration = (screenONEndTime - screenONStartTime) / 1000; //seconds
            SharedPreferences prefs = context.getSharedPreferences("Configurations", Context.MODE_PRIVATE);
            int dataSourceId = prefs.getInt("SCREEN_ON_OFF", -1);
            assert dataSourceId != -1;
            DbMgr.saveMixedData(dataSourceId, screenONStartTime, 1.0f, screenONStartTime, screenONEndTime, screenOnDuration);
            //endregion

        } else if (Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_ON)) {
            Log.e(TAG, "Screen ON");
            screenONStartTime = System.currentTimeMillis();
        }
    }
}
