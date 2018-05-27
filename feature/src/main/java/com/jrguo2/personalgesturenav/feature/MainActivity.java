package com.jrguo2.personalgesturenav.feature;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.jrguo2.personalgesturenav.overlay.OverlayService;



public class MainActivity extends Activity {

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 10101;
    public static final int MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();
    }

    public void checkPermission(){
        try{
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
            else{
                launchOverlayService();
            }
        }
        catch(Exception e){

            Log.e("Permission", "Error ocurred during overlay settings.\n" + e.toString());
        }

    }

    public void launchOverlayService(){
        Intent svc = new Intent(this, OverlayService.class);
        startService(svc);
        finish();
    }

    public void requestPermission(){
        // Here, thisActivity is the current activity
        String PERMISSION = Manifest.permission.SYSTEM_ALERT_WINDOW;
        if (ContextCompat.checkSelfPermission(this, PERMISSION) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    PERMISSION)) {
                // Show an explanation to the user *asynchronously*
                //TODO: Add something here later
                Log.i("Permission", "Requesting Permission");
                //Toast.makeText(MainActivity.this, "Requesting Permission!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION},
                        MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW);
            } else {
                // No explanation needed; request the permission
                Log.i("Permission", "Requesting Permission");
                //Toast.makeText(MainActivity.this, "Requesting Permission!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION},
                        MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW);
            }
        } else {
            // Permission has already been granted
            Log.i("Permissions", "Permission granted.");
            Toast.makeText(MainActivity.this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
            checkPermission();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                launchOverlayService();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    Log.i("Permissions", "Permission granted.");
                    launchOverlayService();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.i("Permissions", "No permission granted.");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
