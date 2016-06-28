package com.example.yql.todoapp.crash;

import com.example.yql.todoapp.deviceinfo.DeviceInfoManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yanqilong on 6/26/16.
 */
public class NCrashPrinter {

    private Settings settings;

    private DeviceInfoManager deviceInfoManager;

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char VERTICAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    public Settings init() {
        if(settings == null) {
            //settings = new Settings();
        }
        if(deviceInfoManager == null) {
            deviceInfoManager = DeviceInfoManager.getInstance();
            deviceInfoManager.init();
        }
        return settings;
    }

    public StringBuilder crashContent() {
        StringBuilder sb = new StringBuilder();

        //Crash top board
        sb = crashTopBoard(sb);

        if(settings.isPrintBuildInfo()) {
            sb = crashTopBoardSub(sb, "Build Information");
            sb = buildInformation(sb);
        }

//        if(settings.isPrintAndroidInfo()) {
//            sb = androidInformation(sb);
//        }

        //if(settings.is)

        sb = crashBottomBorder(sb);
        return sb;
    }

    private StringBuilder crashTopBoard(StringBuilder sb) {
        sb.append(TOP_BORDER)
                .append("\n");
        sb.append("║ ")
                .append("Crash Log")
                .append("\n");
        sb.append(MIDDLE_BORDER)
                .append("\n");
        return sb;
    }

    private StringBuilder crashTopBoardSub(StringBuilder sb, String subTitle) {
//        sb.append(TOP_BORDER)
//                .append("\n");
        sb.append("║  ")
                .append(subTitle)
                .append("\n");
        sb.append(MIDDLE_BORDER)
                .append("\n");
        return sb;
    }

    private StringBuilder buildInformation(StringBuilder sb) {
        Map<String, String> buildMap = deviceInfoManager.buildInformation();

        for(Map.Entry<String, String> entry : buildMap.entrySet()) {
            sb.append("║    ")
                    .append(entry.getKey())
                    .append(":")
                    .append(entry.getValue())
                    .append("\n");
            sb.append(MIDDLE_BORDER + "\n");
        }
        return sb;
    }

    private StringBuilder androidInformation(StringBuilder sb) {
        Map<String, String> androidMap = deviceInfoManager.androidInformation();
        for(Map.Entry<String, String> entry : androidMap.entrySet()) {
            sb.append(entry.getKey() + " : " + entry.getValue());
        }
        return sb;
    }

    private StringBuilder memoryInformation(StringBuilder sb) {
        Map<String, String> memoryMap = deviceInfoManager.memoryInformation();
        for(Map.Entry<String, String> entry : memoryMap.entrySet()) {
            sb.append(entry.getKey() + " : " + entry.getValue());
        }
        return sb;
    }

    private StringBuilder storageInformation(StringBuilder sb) {
        Map<String, String> storageMap = deviceInfoManager.storageInformation();
        for(Map.Entry<String, String> entry : storageMap.entrySet()) {
            sb.append(entry.getKey() + " : " + entry.getValue());
        }
        return sb;
    }

    private StringBuilder screenInformation(StringBuilder sb) {
        Map<String, String> screenMap = deviceInfoManager.screenInformation();
        for(Map.Entry<String, String> entry : screenMap.entrySet()) {
            sb.append(entry.getKey() + " : " + entry.getValue());
        }
        return sb;
    }

    private StringBuilder crashBottomBorder(StringBuilder sb) {

        sb.append("║ ")
                .append("Crash End")
                .append("\n");
        sb.append(BOTTOM_BORDER);
        return sb;
    }


}
