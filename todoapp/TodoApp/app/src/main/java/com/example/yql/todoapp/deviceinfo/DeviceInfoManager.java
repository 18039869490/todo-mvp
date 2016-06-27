package com.example.yql.todoapp.deviceinfo;

import android.content.Context;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by yanqilong on 6/25/16.
 */
public class DeviceInfoManager {

    private IDeviceInfo iDeviceInfo;

    private DeviceInfoManager() {
        //throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static DeviceInfoManager instance = new DeviceInfoManager();

    public static DeviceInfoManager getInstance() {
        return instance;
    }

    public IDeviceInfo init() {
        if(iDeviceInfo == null) {
            iDeviceInfo = new DeviceInfo();
        }
        return iDeviceInfo;
    }

    public Map<String, String> buildInformation() {
        return iDeviceInfo.buildInformation();
    }

    public Map<String, String> androidInformation() {
        return iDeviceInfo.androidInformation();
    }

    public Map<String, String> memoryInformation() {
        return iDeviceInfo.memoryInformation();
    }

    public Map<String, String> storageInformation() {
        return iDeviceInfo.storageInformation();
    }

    public Map<String, String> screenInformation() {
        return iDeviceInfo.screenInformation();
    }

//    public static Map<String, String> appInformation(Context context) {
//        Map<String, String> appMap = new HashMap<String, String>();
//        appMap.put("AppName", AppUtils.getInstance().getAppName(context));
//        appMap.put("VersionCode", AppUtils.getInstance().getVersionCode(context));
//        appMap.put("VersionName", AppUtils.getInstance().getVersionCode(context));
//        return appMap;
//    }
}
