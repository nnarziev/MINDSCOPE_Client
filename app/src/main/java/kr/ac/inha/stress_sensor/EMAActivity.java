package kr.ac.inha.stress_sensor;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import kr.ac.inha.stress_sensor.services.SendGPSStats;

import static kr.ac.inha.stress_sensor.MainActivity.PERMISSIONS;
import static kr.ac.inha.stress_sensor.services.CustomSensorsService.EMA_NOTIFICATION_ID;
import static kr.ac.inha.stress_sensor.services.CustomSensorsService.SERVICE_START_X_MIN_BEFORE_EMA;

public class EMAActivity extends AppCompatActivity {

    //region Constants
    public static final String TAG = "EMAActivity";
    public static final Short[] EMA_NOTIF_HOURS = {8, 11, 14, 17, 20, 23};  //in hours of day
    public static final long[] EMA_NOTIF_MILLIS = new long[]{EMA_NOTIF_HOURS[0] * 3600 * 1000, EMA_NOTIF_HOURS[1] * 3600 * 1000, EMA_NOTIF_HOURS[2] * 3600 * 1000, EMA_NOTIF_HOURS[3] * 3600 * 1000, EMA_NOTIF_HOURS[4] * 3600 * 1000, EMA_NOTIF_HOURS[5] * 3600 * 1000};  //in milliseconds
    //endregion

    //region UI  variables
    TextView question1;
    TextView question2;
    TextView question3;
    TextView question4;

    SeekBar seekBar1;
    SeekBar seekBar2;
    SeekBar seekBar3;
    SeekBar seekBar4;

    Button btnSubmit;
    //endregion
    private short emaOrder;

    private SharedPreferences loginPrefs;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Tools.hasPermissions(this, PERMISSIONS)) {
            dialog = Tools.requestPermissions(EMAActivity.this);
        }
        loginPrefs = getSharedPreferences("UserLogin", MODE_PRIVATE);
        if (!loginPrefs.getBoolean("logged_in", false)) {
            finish();
        }
        setContentView(R.layout.activity_ema);
        init();
    }

    public void init() {
        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);

        seekBar1 = findViewById(R.id.scale_q1);
        seekBar2 = findViewById(R.id.scale_q2);
        seekBar3 = findViewById(R.id.scale_q3);
        seekBar4 = findViewById(R.id.scale_q4);

        btnSubmit = findViewById(R.id.btn_submit);

        emaOrder = getIntent().getShortExtra("ema_order", (short) -1);
    }

    public void clickSubmit(View view) {

        long timestamp = System.currentTimeMillis();

        int answer1 = seekBar1.getProgress();
        int answer2 = seekBar2.getProgress();
        int answer3 = 4;
        int answer4 = 4;
        switch (seekBar3.getProgress()) {
            case 0:
                answer3 = 4;
                break;
            case 1:
                answer3 = 3;
                break;
            case 2:
                answer3 = 2;
                break;
            case 3:
                answer3 = 1;
                break;
            case 4:
                answer3 = 0;
                break;
        }
        switch (seekBar4.getProgress()) {
            case 0:
                answer4 = 4;
                break;
            case 1:
                answer4 = 3;
                break;
            case 2:
                answer4 = 2;
                break;
            case 3:
                answer4 = 1;
                break;
            case 4:
                answer4 = 0;
                break;
        }

        String answers = String.format(Locale.US, "%d %d %d %d",
                answer1,
                answer2,
                answer3,
                answer4);
        SharedPreferences prefs = getSharedPreferences("Configurations", Context.MODE_PRIVATE);
        int dataSourceId = prefs.getInt("SURVEY_EMA", -1);
        assert dataSourceId != -1;
        DbMgr.saveMixedData(dataSourceId, timestamp, 1.0f, timestamp, emaOrder, answers);

        //sending GPS statistics every notification time
        Intent gpsIntent = new Intent(EMAActivity.this, SendGPSStats.class);
        startService(gpsIntent);


        final long app_usage_time_end = System.currentTimeMillis();
        final long app_usage_time_start = (app_usage_time_end - SERVICE_START_X_MIN_BEFORE_EMA * 60 * 1000) + 1000; // add one second to start time
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
                        if (inRange(start_time, app_usage_time_start, app_usage_time_end) && inRange(end_time, app_usage_time_start, app_usage_time_end))
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

        SharedPreferences.Editor editor = loginPrefs.edit();
        editor.putBoolean("ema_btn_make_visible", false);
        editor.apply();


        //go to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(EMA_NOTIFICATION_ID);
        }

        Toast.makeText(this, "Response saved", Toast.LENGTH_SHORT).show();
    }

    private boolean inRange(long value, long start, long end) {
        return start < value && value < end;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
