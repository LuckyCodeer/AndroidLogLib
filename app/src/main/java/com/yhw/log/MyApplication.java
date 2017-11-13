package com.yhw.log;

import android.app.Application;
import android.os.Environment;

import com.yhw.loglib.Logger;
import com.yhw.loglib.config.LogConfig;

/**
 * Author: yhw on 2017-11-13.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化日志配置
        LogConfig logConfig = new LogConfig.Builder()
                .setLog(true) //设置日志打印开关 默认为true
                .setTAG("AAAAA") //统一设置日志TAG 默认为LOG_TAG
                .setSaveFile(true) //设置日志是否保存到文件 默认为false
                //设置日志保存路径，在设置setSaveFile为true的情况下必须设置该路径
                .setLogPath(Environment.getExternalStorageDirectory().getPath())
                //设置日志名称 默认为当天的日期如2017-11-13，设置后变为log_2017-11-13
                .setLogFileName("log")
                .setMaxSaveDay(7)//设置日志在客户端最大保存天数 默认为7天
                .setUploadUrl("http://xxx") //设置日志上传服务器地址 不上传的情况下可以不设置
                .build();
        //设置日志配置
        Logger.init(logConfig);
    }
}
