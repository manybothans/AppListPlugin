package com.phonegap.plugins.applist;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class AppListPlugin extends CordovaPlugin {
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        try {
            if (action.equals("getAppList")) { 
                
                PackageManager packageManager = this.cordova.getActivity().getPackageManager();
                List<ApplicationInfo> applist = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

                JSONObject responseDetailsJson = new JSONObject();
                JSONArray jsonArray = new JSONArray();

                for (ApplicationInfo appInfo : applist) {
                    if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {

                        // Installed by user

                        JSONObject appDetailsJson = new JSONObject();
                        appDetailsJson.put("processName", appInfo.processName);
                        appDetailsJson.put("packageName", appInfo.packageName);

                        callbackContext.success(appInfo.packageName);

                        jsonArray.put(appDetailsJson);

                    } else {
                        // System application
                    }
                }

                responseDetailsJson.put("apps", jsonArray);
                callbackContext.success(responseDetailsJson);
                return true;
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            callbackContext.error(e.getMessage());
            return false;
        } 
    }
}