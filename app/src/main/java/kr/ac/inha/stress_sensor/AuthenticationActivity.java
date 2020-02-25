package kr.ac.inha.stress_sensor;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import inha.nsl.easytrack.ETServiceGrpc;
import inha.nsl.easytrack.EtService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import static kr.ac.inha.stress_sensor.LocationsSettingActivity.GEOFENCE_RADIUS_DEFAULT;
import static kr.ac.inha.stress_sensor.MainActivity.PERMISSIONS;

public class AuthenticationActivity extends Activity {

    private static final int RC_OPEN_ET_AUTHENTICATOR = 100;
    private static final int RC_OPEN_MAIN_ACTIVITY = 101;
    private static final int RC_OPEN_APP_STORE = 102;

    // region Variables
    public static final String TAG = "AuthenticationActivity";
    private SharedPreferences loginPrefs;

    public static final String user_id = "userId", usrEmail = "email", name = "fullname";
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        if (authAppIsNotInstalled()) {
            Toast.makeText(this, "Please install the EasyTrack Authenticator and reopen the application!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=inha.nsl.easytrack"));
            intent.setPackage("com.android.vending");
            startActivityForResult(intent, RC_OPEN_APP_STORE);
        } else if (!Tools.hasPermissions(this, PERMISSIONS)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permissions")
                    .setMessage("You have to grant permissions first!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Tools.grantPermissions(AuthenticationActivity.this, PERMISSIONS);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null).show();
        } else {
            loginPrefs = getApplicationContext().getSharedPreferences("UserLogin", MODE_PRIVATE);
            if (loginPrefs.getBoolean("logged_in", false)) {
                if (getIntent().getBooleanExtra("fromReboot", false))
                    resetGeofences();
                startMainActivity();
            }


        }


    }

    public void authenticateClick(View view) {

        if (authAppIsNotInstalled())
            Toast.makeText(this, "Please install the EasyTrack Authenticator and reopen the application!", Toast.LENGTH_SHORT).show();
        else {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("inha.nsl.easytrack");
            if (launchIntent != null) {
                launchIntent.setFlags(0);
                startActivityForResult(launchIntent, RC_OPEN_ET_AUTHENTICATOR);
            }
        }
    }

    private void resetGeofences() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        for (LocationsSettingActivity.StoreLocation location : LocationsSettingActivity.ALL_LOCATIONS) {
            if (LocationsSettingActivity.getLocationData(getApplicationContext(), location) != null) {
                GeofenceHelper.startGeofence(this,
                        Objects.requireNonNull(LocationsSettingActivity.getLocationData(getApplicationContext(), location)).getmId(),
                        Objects.requireNonNull(LocationsSettingActivity.getLocationData(getApplicationContext(), location)).getmLatLng(),
                        GEOFENCE_RADIUS_DEFAULT);
                Log.e(TAG, "Geofences are reset");
            } else
                Log.e(TAG, "No Geofences in shared preferences");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_OPEN_ET_AUTHENTICATOR) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    final String fullName = data.getStringExtra("fullName");
                    final String email = data.getStringExtra("email");
                    final int userId = data.getIntExtra("userId", -1);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ManagedChannel channel = ManagedChannelBuilder.forAddress(
                                    getString(R.string.grpc_host),
                                    Integer.parseInt(getString(R.string.grpc_port))
                            ).usePlaintext().build();

                            ETServiceGrpc.ETServiceBlockingStub stub = ETServiceGrpc.newBlockingStub(channel);
                            EtService.BindUserToCampaignRequestMessage requestMessage = EtService.BindUserToCampaignRequestMessage.newBuilder()
                                    .setUserId(userId)
                                    .setEmail(email)
                                    .setCampaignId(Integer.parseInt(getString(R.string.stress_campaign_id)))
                                    .build();

                            try {
                                final EtService.BindUserToCampaignResponseMessage responseMessage = stub.bindUserToCampaign(requestMessage);
                                if (responseMessage.getDoneSuccessfully())
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AuthenticationActivity.this, "Successfully authorized and connected to the EasyTrack campaign!", Toast.LENGTH_SHORT).show();
                                            loginPrefs = getApplicationContext().getSharedPreferences("UserLogin", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = loginPrefs.edit();
                                            editor.putString(name, fullName);
                                            editor.putString(usrEmail, email);
                                            editor.putInt(user_id, userId);
                                            if (!loginPrefs.getBoolean("logged_in", false)) {
                                                resetGeofences();
                                            }
                                            editor.putBoolean("logged_in", true);
                                            editor.apply();
                                            startMainActivity();
                                        }
                                    });
                                else
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Calendar cal = Calendar.getInstance();
                                            cal.setTimeInMillis(responseMessage.getCampaignStartTimestamp());
                                            String txt = String.format(Locale.getDefault(), "EasyTrack campaign hasn't started. Campaign start time is: %s",
                                                    SimpleDateFormat.getDateTimeInstance().format(cal.getTime()));
                                            Toast.makeText(AuthenticationActivity.this, txt, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            } catch (StatusRuntimeException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AuthenticationActivity.this, "An error occurred when connecting to the EasyTrack campaign. Please try again later!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Log.e(TAG, "onCreate: gRPC server unavailable");
                            }
                        }
                    }).start();
                }
            } else if (resultCode == Activity.RESULT_FIRST_USER)
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Technical issue. Please check your internet connectivity and try again!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == RC_OPEN_MAIN_ACTIVITY) {
            finish();
        } else if (requestCode == RC_OPEN_APP_STORE) {
            if (authAppIsNotInstalled())
                finish();
        }
    }

    private boolean authAppIsNotInstalled() {
        try {
            getPackageManager().getPackageInfo("inha.nsl.easytrack", 0);
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(0);
        startActivityForResult(intent, RC_OPEN_MAIN_ACTIVITY);
        overridePendingTransition(0, 0);
    }

}
