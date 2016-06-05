package com.example.yql.todoapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Activity基类
 *
 * Author：wave
 * Date：2016/5/28
 * Description：
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    private final static String TAG = "Base";

    //状态栏模式
    public static final int FULL_SCREEN_MODE = 1;
    public static final int SPLIT_MODE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        //Set up system status
        resetTranslucentStatus();
    }

    //------------------------------abstract interface---------------------------------------
    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();
    /**
     * Set up status bar mode
     */
    protected abstract void resetTranslucentStatus();



    //------------------------------Set up status bar--------------------------------------
    /**
     * 设置系统状态栏
     *
     * @param mode
     * @param isColour
     * @param color
     */
    protected void setTranslucentStatus(int mode, boolean isColour, int color) {
        switch (mode) {
            case FULL_SCREEN_MODE:
                setTranslucentFullScreenStatus();
                break;
            case SPLIT_MODE:
                setTranslucentSplitStatus();
                if(isColour) {
                    setTranslucentSplitStatusColour(color);
                }
                break;
            default:
        }
    }

    /**
     * 设置状态栏和标题栏颜色相同
     *
     * 支持Android5.0及以上
     * Only api level in 21
     */
    private void setTranslucentFullScreenStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /**
             * 设置透明状态栏,这样才能让 ContentView 向上
             * Translucent status bar
             */
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 状态栏使用默认着色
     */
    private void setTranslucentSplitStatus() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 自定义着色状态栏，只有model参数为2的实时生效
     * @param color
     *
     * 支持Android5.0及以上
     * Only api level in 21
     */
    private void setTranslucentSplitStatusColour(int color) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(ContextCompat.getColor(BaseAppCompatActivity.this, color));
        }
    }

    //------------------------------Transition between activity and activity--------------------------------------

    /**
     * Same as {@link #startActivity(Class, Bundle)} with no options specified.
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * Start activity with bundle
     * @param cls
     * @param bundle
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * activity by this way to start that can reviver return val
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, requestCode, null);
    }

    /**
     *
     * @param cls
     * @param requestCode
     * @param bundle
     */
    public void startActivityForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

}
