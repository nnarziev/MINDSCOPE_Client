package kr.ac.inha.stress_sensor.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import kr.ac.inha.stress_sensor.DbMgr;


public class StationaryDetector extends Service implements SensorEventListener {

    protected final String TAG = getClass().getSimpleName();

    private long stationaryDetectStartTime;
    private long stationaryDetectEndTime;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stationaryDetectStartTime = System.currentTimeMillis();
        stationaryDetectEndTime = System.currentTimeMillis();
        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTimestamp = System.currentTimeMillis();
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float diff = (float) Math.sqrt(x * x + y * y + z * z);

            if (diff > 0.5) // 0.5 is a threshold, you can test it and change it
            {
                if (curTimestamp - stationaryDetectEndTime > 5000) {
                    stationaryDetectEndTime = curTimestamp;
                    long duration = (stationaryDetectEndTime - stationaryDetectStartTime - 5000) / 1000; // in seconds
                    if (duration > 0) {
                        long start_time = stationaryDetectStartTime - 5000;
                        long end_time = stationaryDetectEndTime;
                        SharedPreferences prefs = getSharedPreferences("Configurations", Context.MODE_PRIVATE);
                        int dataSourceId = prefs.getInt("DATA_SRC_STATIONARY_DUR", -1);
                        assert dataSourceId != -1;
                        DbMgr.saveMixedData(dataSourceId, start_time, 1.0f, start_time, end_time, duration);
                        stationaryDetectStartTime = curTimestamp;
                    }
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this, accelerometer);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
