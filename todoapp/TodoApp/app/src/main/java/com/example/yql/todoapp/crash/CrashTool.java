package com.example.yql.todoapp.crash;

/**
 * Author：wave
 * Date：2016/6/27
 * Description：
 */
public interface CrashTool {
    /**
     * 存储到本地
     */
    void storageToLocal();

    /**
     * 存储到远程服务器
     */
    void storageToRemote();
}
