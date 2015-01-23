package org.apache.cordova;

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

        callbackContext.success("1");        
        try {
        callbackContext.success("2");   
            if (ACTION_GET_APP_LIST.equals(action)) { 
        callbackContext.success("3");   
                PackageManager packageManager = getPackageManager();
        callbackContext.success("4");   
                List<ApplicationInfo> applist = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        callbackContext.success("5");   
                JSONObject responseDetailsJson = new JSONObject();
        callbackContext.success("6");   
                JSONArray jsonArray = new JSONArray();
        callbackContext.success("7");   

                for (ApplicationInfo appInfo : applist) {
        callbackContext.success("8");   
                    if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
        callbackContext.success("9");   
                        // Installed by user
                        Log.w(WARN,appInfo.packageName)
        callbackContext.success("10");   
                        JSONObject appDetailsJson = new JSONObject();
        callbackContext.success("11");   
                        appDetailsJson.put("processName", appInfo.processName);
        callbackContext.success("12");   
                        appDetailsJson.put("packageName", appInfo.packageName);
        callbackContext.success("13");   

                        jsonArray.add(appDetailsJson);
        callbackContext.success("14");
                    } else {
                        // System application
                    }
                }
                responseDetailsJson.put("apps", jsonArray);
        callbackContext.success("15");
                callbackContext.success(responseDetailsJson);
        callbackContext.success("16");
                return true;
            }
            callbackContext.error("Invalid action");
        callbackContext.success("17");
            return false;
        } catch(Exception e) {
            Log.w(WARN,"Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        } 
    }
}