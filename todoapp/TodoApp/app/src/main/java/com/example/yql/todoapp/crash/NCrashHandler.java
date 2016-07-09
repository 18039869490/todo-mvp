package com.example.yql.todoapp.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;


import com.example.yql.todoapp.logger.Logger;
import com.example.yql.todoapp.utils.SDCardUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Custom exception class to caught crash info.
 *
 * Created by yanqilong on 6/23/16.
 */
public class NCrashHandler implements Thread.UncaughtExceptionHandler{

    private Context mContext;
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";

    /**
     * Single pattern
     */
    private NCrashHandler() {
    }
    private static NCrashHandler instance = new NCrashHandler();
    /* Save only one instance of NCrashHandler in Application*/
    public static NCrashHandler getInstance() {
        return instance;
    }

    /**
     * 为应用程序设置自定义Crash处理
     * @param context
     */
    public void setDefaultCrashHandler(Context context) {
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Crasher crasher = Crasher.getInstance();
        crasher.init();
        StringBuilder sb = crasher.printCrashLog();
        sb = softwareInfo(mContext, sb);
        sb = exceptionInfo(ex, sb);
        saveInfoToSD(sb);
    }

    /**
     * Caught system exception and print error info to console
     * @param throwable
     * @return
     */
    private String exceptionInfo(Throwable throwable) {
        StringWriter mStringWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
        throwable.printStackTrace(mPrintWriter);
        mPrintWriter.close();
        Logger.e(mStringWriter.toString());
        return mStringWriter.toString();
    }

    /**
     * 获取一些简单的信息,软件版本
     * @param context
     * @return
     */
    private StringBuilder softwareInfo(Context context, StringBuilder sb){
        HashMap<String, String> map = new HashMap<String, String>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sb.append("Software Information")
                .append("\n")
                .append("versionName:" + mPackageInfo.versionName)
                .append("\n")
                .append("versionCode:" + mPackageInfo.versionCode)
                .append("\n");
        return sb;
    }

    /**
     * 异常信息
     * @param throwable
     * @param sb
     * @return
     */
    private StringBuilder exceptionInfo(Throwable throwable, StringBuilder sb) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        throwable.printStackTrace(pw);
        pw.close();
        Logger.init();
        Logger.e(sw.toString());
        sb.append(SINGLE_DIVIDER)
                .append("\n")
                .append(sw.toString())
                .append("\n");
        return sb;
    }


    private void saveInfoToSD(StringBuilder sb) {
        String fileName;
        FileOutputStream fos = null;

        if(SDCardUtils.getInstance().isSDCardEnable()) {
            Boolean isSuccess = true;
            File dir = new File(SDCardUtils.getInstance().getSDCardPath() + File.separator + "crash" + File.separator);
            if(!dir.exists()) {
                isSuccess = dir.mkdirs();
            }

            if(isSuccess) {
                fileName = dir.toString() + File.separator + parserTime(System.currentTimeMillis());
                File file = new File(fileName);
                try {
                    if(!file.exists()) {
                        file.createNewFile();
                        fos = new FileOutputStream(fileName);
                        fos.write(sb.toString().getBytes());
                        fos.flush();
                        fos.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 将毫秒数转换成yyyy-MM-dd-HH-mm-ss的格式
     * @param milliseconds
     * @return
     */
    private String parserTime(long milliseconds) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String times = format.format(new Date(milliseconds));

        return times;
    }
}
