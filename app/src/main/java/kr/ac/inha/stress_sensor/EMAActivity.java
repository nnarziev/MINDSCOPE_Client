package kr.ac.inha.stress_sensor;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static kr.ac.inha.stress_sensor.services.MainService.EMA_NOTIFICATION_ID;

public class EMAActivity extends AppCompatActivity {

    //region Constants
    public static final String TAG = "EMAActivity";
    public static final Short[] EMA_NOTIF_HOURS = {11, 15, 19, 23};  //in hours of day
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
    private int emaOrder;

    private SharedPreferences loginPrefs;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Tools.hasPermissions(this, Tools.PERMISSIONS)) {
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

        emaOrder = getIntent().getIntExtra("ema_order", (short) -1);
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
