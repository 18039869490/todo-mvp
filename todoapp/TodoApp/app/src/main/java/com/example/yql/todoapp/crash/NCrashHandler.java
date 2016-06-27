package com.example.yql.todoapp.crash;

import android.content.Context;


import com.example.yql.todoapp.logger.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Custom exception class to caught crash info.
 *
 * Created by yanqilong on 6/23/16.
 */
public class NCrashHandler implements Thread.UncaughtExceptionHandler{

    private Context mContext;

    /**
     * Single pattern
     */
    private NCrashHandler() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    private static NCrashHandler instance = new NCrashHandler();
    /* Save only one instance of NCrashHandler in Application*/
    private NCrashHandler getInstance() {
        return instance;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

    }

    /**
     * Caught system exception and print error info to console
     * @param throwable
     * @return
     */
    private String getExceptionInfo(Throwable throwable) {
        StringWriter mStringWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
        throwable.printStackTrace(mPrintWriter);
        mPrintWriter.close();
        Logger.e(mStringWriter.toString());
        return mStringWriter.toString();
    }

}
