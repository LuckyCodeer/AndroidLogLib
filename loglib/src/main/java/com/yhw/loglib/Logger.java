package com.yhw.loglib;

import android.util.Log;

import com.yhw.loglib.config.LogConfig;

/**
 * Author: yhw on 2017-11-09.
 */

public class Logger {

    public static void i(String tag, String msg){
        if(LogConfig.isLog){
            Log.i(tag,msg);
        }
    }

    public static void d(String tag, String msg){
        if(LogConfig.isLog){
            Log.d(tag,msg);
        }
    }

    public static void v(String tag, String msg){
        if(LogConfig.isLog){
            Log.v(tag,msg);
        }
    }

    public static void w(String tag, String msg){
        if(LogConfig.isLog){
            Log.w(tag,msg);
        }
    }

    public static void e(String tag, String msg){
        if(LogConfig.isLog){
            Log.e(tag,msg);
        }
    }

}
