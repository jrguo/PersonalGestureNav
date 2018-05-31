package com.jrguo2.personalgesturenav.feature;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.jrguo2.personalgesturenav.overlay.OverlayService;

import java.util.List;

import static android.provider.Settings.Secure.ACCESSIBILITY_ENABLED;


public class MainActivity extends Activity {

    public static int ACTION_ACCESSIBILITY_PERMISSIONS = 1234;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 10101;
    public static final int MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();
    }

    // To check if service is enabled
    private boolean isAccessibilitySettingsOn(Context mContext) {
        String TAG = "Accessibility Service";
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + OverlayService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }

    public void checkPermission(){
        try{
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
                return;
            }

            if(!isAccessibilitySettingsOn(getApplicationContext())){
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivityForResult(intent, ACTION_ACCESSIBILITY_PERMISSIONS);
                return;
            }

            launchOverlayService();


        }
        catch(Exception e){

            Log.e("Permission", "Error occurred during overlay settings.\n" + e.toString());
        }

    }

    public void launchOverlayService(){
        Log.i("Service", "Launching service");
        Intent svc = new Intent(this, OverlayService.class);
        startService(svc);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                launchOverlayService();
            }
        }
        if (requestCode == ACTION_ACCESSIBILITY_PERMISSIONS){
            if(isAccessibilitySettingsOn(getApplicationContext())){
                Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                launchOverlayService();
                return;
            }
        }

        checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        checkPermission();
    }
}
