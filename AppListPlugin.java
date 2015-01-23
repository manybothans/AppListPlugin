package com.appstars.applist;

import android.util.Log
import java.util.List;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class AppListPlugin extends CordovaPlugin {
    public static final String ACTION_GET_APP_LIST = "getAppList";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (ACTION_GET_APP_LIST.equals(action)) { 
                PackageManager packageManager = getPackageManager();
                List<ApplicationInfo> applist = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

                JSONObject responseDetailsJson = new JSONObject();
                JSONArray jsonArray = new JSONArray();

                for (ApplicationInfo appInfo : applist) {
                    if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
                        // Installed by user
                        Log.w(WARN,appInfo.packageName)
                        JSONObject appDetailsJson = new JSONObject();
                        appDetailsJson.put("processName", appInfo.processName);
                        appDetailsJson.put("packageName", appInfo.packageName);

                        jsonArray.add(appDetailsJson);
                    } else {
                        // System application
                    }
                }
                responseDetailsJson.put("apps", jsonArray);
                callbackContext.success(responseDetailsJson);
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            Log.w(WARN,"Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        } 
    }
}