package com.example.yql.todoapp.logger;

/**
 * Created by yanqilong on 6/19/16.
 */
public class Logger {
    private static final String DEFAULT_TAG = "PERTTYLOGGER";
    private static Printer printer;

    //no instance
    private Logger() {}

    /**
     * It is used to get the settings object in order to change settings
     * @return the settings object
     */
    public static Settings init() {
        return init(DEFAULT_TAG);
    }

    public static Settings init(String tag) {
        if(printer == null) {
            printer = new LoggerPrinter();
        }
        return printer.init(tag);
    }

    public static void clear() {
        printer.clear();
        printer = null;
    }

    public static Printer t(String tag) {
        return printer.t(tag, printer.getSettings().getMethodCount());
    }

    public static Printer t(String tag, int methodCount) {
        return printer.t(tag, methodCount);
    }

    public static void v(String message, Object... args) {
        printer.v(message, args);
    }

    public static void d(String message, Object... args) {
        printer.d(message, args);
    }

    public static void i(String message, Object... args) {
        printer.i(message, args);
    }

    public static void w(String message, Object... args) {
        printer.w(message, args);
    }

    public static void e(String message, Object... args) {
        printer.e(message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        printer.e(throwable, message, args);
    }

    public static void wtf(String message, Object... args) {
        printer.wtf(message, args);
    }

    /**
     * Formats the json content and print it.
     * @param json
     */
    public static void json(String json) {
        printer.json(json);
    }

    public static void json(String json, int logType) {
        printer.json(json, logType);
    }

    /**
     * Formats the json content and print it.
     * @param xml
     */
    public static void xml(String xml) {
        printer.xml(xml);
    }

    public static void xml(String xml, int logType) {
        printer.xml(xml, logType);
    }
}
