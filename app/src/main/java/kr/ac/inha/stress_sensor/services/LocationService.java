package kr.ac.inha.stress_sensor.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;

import android.util.Log;


import java.io.FileOutputStream;

import kr.ac.inha.stress_sensor.DbMgr;

public class LocationService extends Service implements LocationListener {

    //region Constants
    public static final String TAG = "LocationService";
    public static final String LOCATIONS_TXT = "locations.txt";
    private static final int LOCATION_UPDATE_MIN_INTERVAL = 5 * 60 * 1000; //milliseconds
    private static final int LOCATION_UPDATE_MIN_DISTANCE = 10; // meters
    //endregion

    private LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_MIN_INTERVAL, LOCATION_UPDATE_MIN_DISTANCE, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);  //remove location listener
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "Reporting location");
        /*
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(location.getTime());
        String formattedTime = cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + "." + cal.get(Calendar.MILLISECOND);
        */
        long nowTime = System.currentTimeMillis();

        String resultString = location.getTime() + "," + location.getLatitude() + "," + location.getLongitude() + "\n";
        try {
            SharedPreferences prefs = getSharedPreferences("Configurations", Context.MODE_PRIVATE);
            int dataSourceId = prefs.getInt("LOCATION_GPS", -1);
            DbMgr.saveMixedData(dataSourceId, nowTime, location.getAccuracy(), nowTime, location.getLatitude(), location.getLongitude());
            FileOutputStream fileOutputStream = openFileOutput(LOCATIONS_TXT, Context.MODE_APPEND);
            fileOutputStream.write(resultString.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
