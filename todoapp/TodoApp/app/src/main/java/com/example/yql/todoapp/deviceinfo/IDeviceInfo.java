package com.example.yql.todoapp.deviceinfo;

import java.util.Map;

/**
 * Created by yanqilong on 6/25/16.
 */
public interface IDeviceInfo {

    /**
     * Build information
     */
    Map<String, String> buildInformation();

    /**
     * Android information
     * @return
     */
    Map<String, String> androidInformation();

    /**
     * Memory information
     * @return
     */
    Map<String, String> memoryInformation();

    Map<String, String> connectionInformation();

    /**
     * Storage information
     * @return
     */
    Map<String, String> storageInformation();

    Map<String, String> gpuInformation();

    Map<String, String> cpuInformation();

    Map<String, String> sensorInformation();

    /**
     * Screen information
     * @return
     */
    Map<String, String> screenInformation();

    Map<String, String> cameraInformation();
}
