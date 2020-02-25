package kr.ac.inha.stress_sensor.receivers;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ConnectionMonitor extends ConnectivityManager.NetworkCallback {
    private static final String TAG = "ConnectionMonitor";
    private final NetworkRequest networkRequest;

    private Context context;

    public ConnectionMonitor(Context con) {
        context = con;
        networkRequest = new NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build();
    }

    public void enable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerNetworkCallback(networkRequest, this);
    }

    public void disable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(this);
    }


    @Override
    public void onAvailable(Network network) {
        // Do what you need to do here
        Log.e(TAG, "Internet Re-connected");
        //TODO: do smth when internet is connected
    }

}
