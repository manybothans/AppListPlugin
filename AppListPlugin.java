package com.appstars.applist;
 
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

public class AppListPlugin extends CordovaPlugin {
    public static final String ACTION_GET_APP_LIST = "getAppList";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (ACTION_GET_APP_LIST.equals(action)) { 
                JSONObject arg_object = args.getJSONObject(0);

                PackageManager packageManager = getPackageManager();
                List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

                JSONArray json = JSONArray.fromObject(list);
                callbackContext.success(json);
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        } 
    }
}