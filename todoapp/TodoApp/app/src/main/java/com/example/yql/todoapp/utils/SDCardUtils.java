package com.example.yql.todoapp.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.util.Locale;

/**
 * SD card Helper.
 *
 * Created by yanqilong on 6/23/16.
 */
public class SDCardUtils {

    //Capacity unit
    public final static int BYTE = 1;
    public final static int KIB = 2;
    public final static int MIB = 3;
    public final static int GIB = 4;

    /**
     * Single pattern
     */
    private SDCardUtils() {
        //cannot be instantiated
    }

    private static SDCardUtils instance = new SDCardUtils();

    //Save only one instance of SDCardUtils in Application
    public static SDCardUtils getInstance() {
        return instance;
    }

    /**
     * To judge whether or not sd card enable.
     *
     * @return
     */
    public boolean isSDCardEnable() {
        return Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * Get the absolute path for sd card.
     *
     * @return
     */
    public String getSDCardPath() {
        return Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
    }

    /**
     * Get the absolute path for inner storage.
     * @return
     */
    public String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * Get enable capacity from specified path, unit byte
     *
     * @param filePath
     * @param unit
     * @return
     */
    public String getFreeBytes(String filePath, int unit) {

        if(filePath.startsWith(getSDCardPath())) {
            //if filePath prefix is equals getSDCardPath() to get sd card capacity.
            filePath = getSDCardPath();
        } else {
            //internal storage
            filePath = getRootDirectoryPath();
        }

        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocksLong() - 4;
        return formatCapacityByUnit(stat.getBlockSizeLong() * availableBlocks, unit);
    }

    /**
     * Formats capacity
     */
    private String formatCapacityByUnit(long bytes, int unit) {
        float result = 0L;
        String tUnit = null;

        switch (unit) {
            case GIB:
                result = bytes / 1073741824f;
                tUnit = "GB";
                break;
            case MIB:
                result = bytes / 1048576f;
                tUnit = "MB";
                break;
            case KIB:
                result = bytes / 1024f;
                tUnit = "KB";
                break;
            case BYTE:
                result = bytes;
                tUnit = "byte";
                break;
        }
        return String.format(Locale.getDefault(), "%.3f " + tUnit, result);
    }
}
