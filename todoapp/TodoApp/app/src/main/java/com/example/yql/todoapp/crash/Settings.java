package com.example.yql.todoapp.crash;

/**
 * Created by yanqilong on 6/26/16.
 */
public class Settings {
    /**
     * true if to print build info
     */
    private boolean isPrintBuildInfo = true;
    /**
     * true if to print android info
     */
    private boolean isPrintAndroidInfo = true;
    /**
     * true if to print memory info
     */
    private boolean isPrintMemoryInfo = true;
    /**
     * true if to print storage info
     */
    private boolean isPrintStorageInfo = true;
    /**
     * true if to print screen info
     */
    private boolean isPrintScreenInfo = true;
    /**
     * true if to print app info
     */
    private boolean isPrintAppInfo = true;

    public boolean isPrintBuildInfo() {
        return isPrintBuildInfo;
    }

    public void setPrintBuildInfo(boolean printBuildInfo) {
        isPrintBuildInfo = printBuildInfo;
    }

    public boolean isPrintAndroidInfo() {
        return isPrintAndroidInfo;
    }

    public void setPrintAndroidInfo(boolean printAndroidInfo) {
        isPrintAndroidInfo = printAndroidInfo;
    }

    public boolean isPrintMemoryInfo() {
        return isPrintMemoryInfo;
    }

    public void setPrintMemoryInfo(boolean printMemoryInfo) {
        isPrintMemoryInfo = printMemoryInfo;
    }

    public boolean isPrintStorageInfo() {
        return isPrintStorageInfo;
    }

    public void setPrintStorageInfo(boolean printStorageInfo) {
        isPrintStorageInfo = printStorageInfo;
    }

    public boolean isPrintScreenInfo() {
        return isPrintScreenInfo;
    }

    public void setPrintScreenInfo(boolean printScreenInfo) {
        isPrintScreenInfo = printScreenInfo;
    }

    public boolean isPrintAppInfo() {
        return isPrintAppInfo;
    }

    public void setPrintAppInfo(boolean printAppInfo) {
        isPrintAppInfo = printAppInfo;
    }

//    public LogTool getLogTool() {
//        if(logTool == null) {
//            logTool = new AndroidLogTool();
//        }
//        return logTool;
//    }
}
