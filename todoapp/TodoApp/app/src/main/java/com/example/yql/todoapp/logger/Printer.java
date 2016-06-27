package com.example.yql.todoapp.logger;

/**
 * Created by yanqilong on 6/18/16.
 */
public interface Printer {
    Printer t(String tag, int methodCount);

    Settings init(String tag);

    Settings getSettings();

    void v(String message, Object... args);

    void d(String message, Object... args);

    void i(String message, Object... args);

    void w(String message, Object... args);

    void e(String message, Object... args);

    void e(Throwable throwable, String message, Object... args);

    void wtf(String message, Object... args);

    void json(String json);

    void json(String json, int logType);

    void xml(String xml);

    void xml(String xml, int logType);

    void clear();

}
