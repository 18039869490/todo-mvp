package com.example.yql.todoapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Helper of app infomation
 * Created by yanqilong on 6/26/16.
 */
public class AppUtils {
    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static AppUtils instance = new AppUtils();

    public static AppUtils getInstance() {
        return instance;
    }

    /**
     * The name of application
     * @return
     */
    public String getAppName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if(packageInfo != null) {
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        }
        return null;
    }

    /**
     * The version name of application
     * @return
     */
    public String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if(packageInfo != null) {
            return packageInfo.versionName;
        }
        return null;
    }

    /**
     * The version code of application
     * @return
     */
    public String getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if(packageInfo != null) {
            return String.valueOf(packageInfo.versionCode);
        }
        return null;
    }

    private PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
