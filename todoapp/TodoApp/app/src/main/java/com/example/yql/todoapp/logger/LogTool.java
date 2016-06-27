package com.example.yql.todoapp.logger;

/**
 * Created by yanqilong on 6/18/16.
 */
public interface LogTool {
    void v(String tag, String message);

    void d(String tag, String message);

    void i(String tag, String message);

    void w(String tag, String message);

    void e(String tag, String message);

    void wtf(String tag, String message);
}
