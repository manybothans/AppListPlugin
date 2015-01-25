package com.phonegap.plugins.applist;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import android.os.Bundle;

public class AppListPlugin extends CordovaPlugin {

    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            packageManager = getPackageManager();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        try {
            if (action.equals("getAppList")) { 
                
                applist = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

                JSONObject responseDetailsJson = new JSONObject();
                JSONArray jsonArray = new JSONArray();

                for (ApplicationInfo appInfo : applist) {
                    if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {

                        // Installed by user

                        JSONObject appDetailsJson = new JSONObject();
                        appDetailsJson.put("processName", appInfo.processName);
                        appDetailsJson.put("packageName", appInfo.packageName);

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