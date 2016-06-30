package com.example.yql.todoapp.deviceinfo;

import android.os.Build;
import android.os.Environment;


import com.example.yql.todoapp.utils.SDCardUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanqilong on 6/25/16.
 */
public class DeviceInfo implements IDeviceInfo {

    @Override
    public Map<String, String> buildInformation() {
        Map<String, String> buildMap = new HashMap<String, String>();
        buildMap.put("Brand", Build.BRAND);
        buildMap.put("Manufacturer", Build.MANUFACTURER);
        buildMap.put("Model", Build.MODEL);
        buildMap.put("Serial", Build.SERIAL);
        buildMap.put("Time", String.valueOf(Build.TIME));
        return buildMap;
    }

    @Override
    public Map<String, String> androidInformation() {
        Map<String, String> osMap = new HashMap<String, String>();
        osMap.put("Android Version", Build.VERSION.RELEASE);
        osMap.put("API Version", String.valueOf(Build.VERSION.SDK_INT));
        osMap.put("INCREMENTAL", Build.VERSION.INCREMENTAL);
        osMap.put("Codename", Build.VERSION.CODENAME);
        return osMap;
    }

    @Override
    public Map<String, String> memoryInformation() {
        Map<String, String> armMap = new HashMap<String, String>();
        armMap.put("Total Memory", getMemory());
        armMap.put("Heap Size", getHeapSize());
        armMap.put("Heap Start Size", getHeapStartSize());
        armMap.put("Heap Growth Limit", getHeapGrowthLimit());
        return armMap;
    }

    @Override
    public Map<String, String> connectionInformation() {

        return null;
    }

    @Override
    public Map<String, String> storageInformation() {
        Map<String, String> romMap = new HashMap<String, String>();
        romMap.put("Remain Space", SDCardUtils.getInstance().getFreeBytes(SDCardUtils.getInstance().getSDCardPath(), SDCardUtils.GIB));

        return romMap;
    }

    @Override
    public Map<String, String> gpuInformation() {
        return null;
    }

    @Override
    public Map<String, String> cpuInformation() {
        return null;
    }

    @Override
    public Map<String, String> sensorInformation() {
        return null;
    }

    @Override
    public Map<String, String> screenInformation() {
        return null;
    }

    @Override
    public Map<String, String> cameraInformation() {
        return null;
    }

    private String getMemory() {
        String memory = null;

        String[] listMemory = getTotalMemory();
        if(listMemory != null) {
            for(int i = 0; i < listMemory.length; i++) {
                if(listMemory[i].contains("MemTotal")) {
                    memory = listMemory[i];
                }
            }

            if(memory != null) {
                memory = memory.replace("MemTotal:", "").replace(" ", "").replace("kB", "");
                float mem = Float.parseFloat(memory)/ 1000f;
                return String.format("%.3f", mem) + "MB";
            }
        }

        return "unknown";
    }

    /**
     * All of memory information
     * @return
     */
    private String[] getTotalMemory() {
        try {
            Process proc = Runtime.getRuntime().exec("cat /proc/meminfo");
            InputStream is = proc.getInputStream();
            String[] listMemory = getStringFromInputStream(is).split("\n");
            return listMemory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getHeapSize() {
        try {
            Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapsize");
            InputStream is = proc.getInputStream();
            String size = getStringFromInputStream(is);
            if(!size.equals("\n")) {
                return size.replace("\n", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    private String getHeapStartSize() {
        try {
            Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapstartsize");
            InputStream is = proc.getInputStream();
            String size = getStringFromInputStream(is);
            if(!size.equals("\n")) {
                return size.replace("\n", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    private String getHeapGrowthLimit() {
        try {
            Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapgrowthlimit");
            InputStream is = proc.getInputStream();
            String size = getStringFromInputStream(is);
            if(!size.equals("\n")) {
                return size.replace("\n", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    private String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
