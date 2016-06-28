package com.example.yql.todoapp.crash;

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

    @Override
    public void printCrashLog() {
        StringBuilder sb = new StringBuilder();
        //头部格式
        sb = topBoard(sb);
        //平台构建信息
        sb = titleBar(sb, "Build Information");
        sb = buildInformation(sb);
        Log.e("Crash", sb.toString());
    }

    private StringBuilder topBoard(StringBuilder sb) {
        sb.append(TOP_BORDER)
                .append("\n");
        return sb;
    }

    private StringBuilder titleBar(StringBuilder sb, String title) {
        sb.append("║ ")
                .append(title)
                .append("\n")
                .append(MIDDLE_BORDER)
                .append("\n");
        return sb;
    }

    private StringBuilder buildInformation(StringBuilder sb) {
        Map<String, String> buildMap = Settings.getInstance().deviceInfoManager.buildInformation();

        for(Map.Entry<String, String> entry : buildMap.entrySet()) {
            sb.append("║ ")
                    .append(entry.getKey())
                    .append(":")
                    .append(entry.getValue())
                    .append("\n");
            sb.append(MIDDLE_BORDER + "\n");
        }
        return sb;
    }

    //private StringBuilder title

//    private StringBuilder buildInformation(StringBuilder sb) {
//
//    }

}
