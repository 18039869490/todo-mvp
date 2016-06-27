package com.example.yql.todoapp.logger;

/**
 * Created by yanqilong on 6/18/16.
 */
public class Settings {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    private int methodCount = 2;
    private boolean showThreadInfo = true;
    private int methodOffset = 0;
    private LogTool logTool;

    /**
     * Determines how logs will printed
     */
    private LogLevel logLevel = LogLevel.FULL;

    public Settings logTools(LogTool logTool) {
        this.logTool = logTool;
        return this;
    }

    public Settings hideThreadInfo() {
        showThreadInfo = false;
        return this;
    }

    /**
     * Use {@link #methodCount}
     */
    @Deprecated
    public Settings setMethodCount(int methodCount) {
        return methodCount(methodCount);
    }

    public Settings methodCount(int methodCount) {
        if(methodCount < 0) {
            methodCount = 0;
        }
        this.methodCount = methodCount;
        return this;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public int getMethodOffset() {
        return  methodOffset;
    }

    public LogTool getLogTool() {
        if(logTool == null) {
            logTool = new AndroidLogTool();
        }
        return logTool;
    }


}
