package com.example.yql.todoapp.crash;

import android.text.TextUtils;
import android.util.Log;

import com.example.yql.todoapp.logger.Logger;

import java.util.Map;

/**
 * Author：wave
 * Date：2016/6/27
 * Description：
 */
public class CrashPrinter implements Printer {

    /**
     * Print type
     */
    private static final int BUILD = 1;
    private static final int ANDROID = 2;
    private static final int MEMORY = 3;
    private static final int STORAGE = 4;
    private static final int SCREEN = 5;

    /**
     * Drawing toolbox
     */
//    private static final char TOP_LEFT_CORNER = '╔';
//    private static final char BOTTOM_LEFT_CORNER = '╚';
//    private static final char MIDDLE_CORNER = '╟';
//    //private static final char VERTICAL_DOUBLE_LINE = '║';
//    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
//    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
//    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
//    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    @Override
    public StringBuilder printCrashLog() {
        StringBuilder sb = new StringBuilder();
        //头部格式
//        sb = topBoard(sb);

        //硬件平台构建信息
        if(Settings.getInstance().isPrintBuildInfo()) {
            sb = titleBar(sb, "Build Information");
            sb = buildInformation(sb);
        }

        //Android系统信息
        if(Settings.getInstance().isPrintAndroidInfo()) {
            sb = titleBar(sb, "Android Information");
            sb = androidInformation(sb);
        }

        //内存信息
        if(Settings.getInstance().isPrintMemoryInfo()) {
            sb = titleBar(sb, "Memory Information");
            sb = memoryInformation(sb);
        }

        //存储信息
        if(Settings.getInstance().isPrintStorageInfo()) {
            sb = titleBar(sb, "Storage Information");
            sb = storageInformation(sb);
        }

        //屏幕信息
//        if(Settings.getInstance().isPrintScreenInfo()) {
//            sb = titleBar(sb, "Screen Information");
//            sb = screenInformation(sb);
//        }

        //底部信息
//        sb = bottomBoard(sb);
        return sb;
    }

    /**
     * 硬件平台构建信息
     * @param sb
     * @return
     */
    private StringBuilder buildInformation(StringBuilder sb) {
        return chunkContent(sb, BUILD);
    }

    /**
     * Android系统信息
     * @param sb
     * @return
     */
    private StringBuilder androidInformation(StringBuilder sb) {
        return chunkContent(sb, ANDROID);
    }

    /**
     * 内存信息
     * @param sb
     * @return
     */
    private StringBuilder memoryInformation(StringBuilder sb) {
        return chunkContent(sb, MEMORY);
    }

    /**
     * 存储信息
     * @param sb
     * @return
     */
    private StringBuilder storageInformation(StringBuilder sb) {
        return chunkContent(sb, STORAGE);
    }

    /**
     * 屏幕信息
     * @param sb
     * @return
     */
    private StringBuilder screenInformation(StringBuilder sb) {
        return chunkContent(sb, SCREEN);
    }


    /**
     * 头部样式
     * @param sb
     * @return
     */
//    private StringBuilder topBoard(StringBuilder sb) {
//        sb.append(TOP_BORDER)
//                .append("\n");
//        return sb;
//    }

    /**
     * 底部样式
     * @param sb
     * @return
     */
//    private StringBuilder bottomBoard(StringBuilder sb) {
//        return bottomBoard(sb, null);
//    }

//    private StringBuilder bottomBoard(StringBuilder sb, String title) {
//        if(TextUtils.isEmpty(title)) {
//            title = "END PRINT";
//        }
//        sb.append(MIDDLE_BORDER)
//                .append("\n")
//                .append("║ ")
//                .append(title)
//                .append("\n")
//                .append(BOTTOM_BORDER);
//        return sb;
//    }

    /**
     * 块级标题
     * @param sb
     * @param title
     * @return
     */
    private StringBuilder titleBar(StringBuilder sb, String title) {
//        sb.append("║ ")
//                .append(title)
//                .append("\n")
//                .append(MIDDLE_BORDER)
//                .append("\n");

        sb.append(title)
                .append("\n");
        return sb;
    }

    /**
     * 块级内容
     * @return
     */
    private StringBuilder chunkContent(StringBuilder sb, int type) {
        Map<String, String> unMap = null;

        switch (type) {
            case BUILD:
                unMap = Settings.getInstance().deviceInfoManager.buildInformation();
                break;
            case ANDROID:
                unMap = Settings.getInstance().deviceInfoManager.androidInformation();
                break;
            case MEMORY:
                unMap = Settings.getInstance().deviceInfoManager.memoryInformation();
                break;
            case STORAGE:
                unMap = Settings.getInstance().deviceInfoManager.storageInformation();
                break;
            case SCREEN:
                unMap = Settings.getInstance().deviceInfoManager.screenInformation();
                break;
        }


        for(Map.Entry<String, String> entry : unMap.entrySet()) {
            sb.append(entry.getKey())
                    .append(":")
                    .append(entry.getValue())
                    .append("\n");
        }
        sb.append(SINGLE_DIVIDER)
        .append("\n");

        return sb;
    }
}
