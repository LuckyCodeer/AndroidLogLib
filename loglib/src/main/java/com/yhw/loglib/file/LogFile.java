package com.yhw.loglib.file;

import android.util.Log;

import com.yhw.loglib.config.LogConfig;
import com.yhw.loglib.utils.DateUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志保存的文件
 * Author: yhw on 2017-11-10.
 */

public final class LogFile {
    private static final String TAG = LogFile.class.getSimpleName();
    private static LogFile mLogFile;
    private String mOutPutPath; //日志输出路径
    private String mLogFileName; //日志文件名称
    private static final String mExtName = ".log"; //拓展名
    private File mPathFile;
    private File mFile;
    private LogConfig mLogConfig;

    private LogFile(LogConfig logConfig){
        this.mLogConfig = logConfig;
        init();
    }

    public static LogFile getInstance(LogConfig logConfig){
        if(mLogFile==null){
            mLogFile = new LogFile(logConfig);
        }
        return mLogFile;
    }

    private void init(){
        mLogFileName = mLogConfig.getLogFileName();
        mOutPutPath = mLogConfig.getLogPath();
        mFile = createFile();
    }

    /**
     * 初始化日志文件
     */
    private File createFile(){
        try {
            Log.i(TAG,"mOutPutPath: "+mOutPutPath);
            mPathFile = new File(mOutPutPath);
            if(!mPathFile.isDirectory()){
                throw new IllegalArgumentException("日志路径设置错误，必须为目录！");
            }
            boolean b = true;
            if(mPathFile!=null && !mPathFile.exists()){
                b=mPathFile.mkdirs();
            }
            if(!b){
                throw new IllegalArgumentException("日志路径创建失败，将不能保存日志到文件中");
            }

            File logFile = new File(mPathFile,mLogFileName+mExtName);
            boolean b1 = true;
            if(!logFile.exists()){
                b1=logFile.createNewFile();
            }
            if(!b1){
                throw new IllegalArgumentException("日志文件创建失败，将不能保存日志到文件中");
            }

            return logFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入文件
     * @param content 日志内容
     */
    public void writeToFile(String content){
        if(mFile==null || !mFile.exists()){
            Log.i(TAG , "log file is null");
            return;
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(mFile,true);
            fw.write(content);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fw!=null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除最大保留天数之外的日志文件
     */
    public void deleteLogFile(){
        try {
            File[] files = mPathFile.listFiles();
            if(files==null || files.length==0){
                return;
            }
            Date delDate = DateUtil.getDateBefore(mLogConfig.getMaxSaveDay());
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.SIMPLE_FORMAT, Locale.CHINA);
            Log.i(TAG, "delDate: "+dateFormat.format(delDate));
            for (File file : files) {
                String [] fileNames = file.getName().split("_");
                if(fileNames.length>0){
                    String dateTime = fileNames[fileNames.length-1];
                    Log.i(TAG,"dateTime: "+dateTime);
                    Date fileDate = dateFormat.parse(dateTime);
                    if(fileDate.getTime()<=delDate.getTime()){
                        boolean b=file.delete();
                        if(b){
                            Log.i(TAG, "del file: "+file.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getOutPutPath() {
        return mOutPutPath;
    }

    public void setOutPutPath(String mOutPutPath) {
        this.mOutPutPath = mOutPutPath;
    }

    public String getLogFileName() {
        return mLogFileName;
    }

    public void setLogFileName(String mLogFileName) {
        this.mLogFileName = mLogFileName;
    }
}
