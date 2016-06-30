package com.example.yql.todoapp.crash;

/**
 * Created by yanqilong on 6/26/16.
 */
public class Crasher {

    private static CrashPrinter printer;

    private Settings settings;

    private Crasher() {

    }

    private static Crasher instance = new Crasher();

    public static Crasher getInstance() {
        return instance;
    }

    public Settings init() {
        if(printer == null) {
            printer = new CrashPrinter();
        }
        return settings = Settings.getInstance().init();
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /**
     * 捕获设备信息
     */
    public StringBuilder printCrashLog() {
        return  printer.printCrashLog();
    }
}
