package com.yhw.loglib;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.yhw.loglib.config.LogConfig;
import com.yhw.loglib.file.LogFile;
import com.yhw.loglib.http.UploadLog;
import com.yhw.loglib.utils.DateUtil;
import com.yhw.loglib.utils.LogUtil;

/**
 * 日志打印类
 * Author: yhw on 2017-11-09.
 */

public class Logger {
    private static final String TAG = Logger.class.getSimpleName();
    private static LogConfig mLogConfig;
    private static final String INFO = "INFO";
    private static final String VERBOSE = "VERBOSE";
    private static final String DEBUG = "DEBUG";
    private static final String WARN = "WARN";
    private static final String ERROR = "ERROR";

    /**
     * 设置配置文件
     */
    public static void init(LogConfig logConfig){
        if(logConfig==null){
            return;
        }
        mLogConfig = logConfig;
        if(logConfig.isSaveFile()){
            LogFile.getInstance(mLogConfig).deleteLogFile();
        }
    }

    public static void i(String msg){
        print(INFO,msg);
    }

    public static void i(String tag, String msg){
        print(INFO,tag,msg);
    }

    public static void d(String msg){
        print(DEBUG,msg);
    }

    public static void d(String tag, String msg){
        print(DEBUG,tag,msg);
    }

    public static void v(String msg){
        print(VERBOSE,msg);
    }

    public static void v(String tag, String msg){
        print(VERBOSE,tag,msg);
    }

    public static void w(String msg){
        print(WARN,msg);
    }

    public static void w(String tag, String msg){
        print(WARN,tag,msg);
    }

    public static void e(String msg){
        print(ERROR,msg);
    }

    public static void e(String tag, String msg){
        print(VERBOSE,tag,msg);
    }

    private static String getMessage(String msg){
        return msg;
    }

    private static String getFileMessage(String type,String msg){
        return DateUtil.getDate(DateUtil.SIMPLE_FORMAT_1)
                +" "
                +DateUtil.getDate(DateUtil.SIMPLE_FORMAT_2)
                +" "
                +type
                +"："
                +msg
                +"\t\n"
                ;
    }

    /**根据不同类型打印日志*/
    private static void print(String type,String msg){
        print(type,null,msg);
    }

    private static void print(String type,String tag,String msg){
        if(mLogConfig==null){
            throw new IllegalArgumentException("Logger没有初始化成功,LogConfig不能为null！");
        }

        if(mLogConfig.isLog()){
            String t = TextUtils.isEmpty(tag)?mLogConfig.getTAG():tag;
            String message = getMessage(msg);
            if(INFO.equals(type)){
                Log.i(t,message);
            }
            else if(VERBOSE.equals(type)){
                Log.v(t,message);
            }
            else if(DEBUG.equals(type)){
                Log.e(t,message);
            }
            else if(WARN.equals(type)){
                Log.w(t,message);
            }
            else if(ERROR.equals(type)){
                Log.e(t,message);
            }
        }

        if(mLogConfig.isSaveFile()){
            if(!LogUtil.isExistsSdcard()){
                Log.e(mLogConfig.getTAG(),"SdCard not exists!");
                return;
            }

            writeToFile(getFileMessage(type,msg));
        }

    }

    private static void writeToFile(String logMsg){
        LogFile.getInstance(mLogConfig).writeToFile(logMsg);
    }

    /**
     * 上传日志
     * @param context 上下文
     * @param uploadLogListener 上传结果回调接口
     */
    public static void upLoadLog(Context context, UploadLog.UploadLogListener uploadLogListener){
        if(mLogConfig==null){
            throw new IllegalArgumentException("Logger没有初始化成功,LogConfig不能为null！");
        }
        UploadLog uploadLog = new UploadLog(context,uploadLogListener);
        uploadLog.setLogUrl(mLogConfig.getUploadUrl());
        uploadLog.setLogPath(mLogConfig.getLogPath());
        uploadLog.upload();
    }

}
