package com.yhw.loglib.config;

import android.text.TextUtils;

import com.yhw.loglib.utils.DateUtil;

import java.io.File;

/**
 * 日志配置
 * Author: yhw on 2017-11-09.
 */

public class LogConfig {
    /**是否打印开关*/
    private final boolean isLog;
    /**是否将日志保存到文件*/
    private final boolean isSaveFile;
    /**日志TAG名称*/
    private final String TAG;
    /**日志文件名称*/
    private String logFileName;
    /**日志路径名称*/
    private String logPath;
    /**日志最大保存天数 默认7天*/
    private final int maxSaveDay;
    /**日志上传服务器地址*/
    private final String uploadUrl;

    private LogConfig(Builder builder){
        this.isLog = builder.isLog;
        this.isSaveFile = builder.isSaveFile;
        this.TAG = builder.TAG;
        this.logFileName = builder.logFileName;
        this.logPath = builder.logPath;
        this.maxSaveDay = builder.maxSaveDay;
        this.uploadUrl = builder.uploadUrl;
    }

    public boolean isLog() {
        return isLog;
    }

    public boolean isSaveFile() {
        return isSaveFile;
    }

    public String getTAG() {
        return TAG;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public String getLogPath() {
        return logPath;
    }

    public int getMaxSaveDay() {
        return maxSaveDay;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public static final class Builder{
        /**是否打印开关*/
        private boolean isLog = true;
        /**是否将日志保存到文件*/
        private boolean isSaveFile = false;
        /**日志TAG名称*/
        private String TAG = "LOG_TAG";
        /**日志文件名称*/
        private String logFileName;
        /**日志路径名称*/
        private String logPath;
        /**日志最大保存天数 默认7天*/
        private int maxSaveDay = 7;
        /**日志上传服务器地址*/
        private String uploadUrl;

        public Builder setLog(boolean log) {
            isLog = log;
            return this;
        }

        public Builder setSaveFile(boolean saveFile) {
            isSaveFile = saveFile;
            return this;
        }

        public Builder setTAG(String TAG) {
            this.TAG = TAG;
            return this;
        }

        public Builder setLogFileName(String logFileName) {
            this.logFileName = logFileName;
            return this;
        }

        public Builder setLogPath(String logPath) {
            this.logPath = logPath;
            return this;
        }

        public Builder setMaxSaveDay(int maxSaveDay) {
            this.maxSaveDay = maxSaveDay;
            return this;
        }

        public Builder setUploadUrl(String uploadUrl) {
            this.uploadUrl = uploadUrl;
            return this;
        }

        public LogConfig build(){
            LogConfig logConfig = new LogConfig(this);
            if(logConfig.isSaveFile){
                if(TextUtils.isEmpty(logConfig.logPath)){
                    throw new IllegalArgumentException("日志路径设置错误，不能为空！");
                }else{
                    logConfig.logPath = logConfig.logPath+File.separator+"fflog";
                }

                //如果名称设置为空或者没有设置 默认将日志名称设置为当前日期
                if(TextUtils.isEmpty(logConfig.logFileName)){
                    logConfig.logFileName = DateUtil.getDate();
                }else{
                    logConfig.logFileName = logConfig.logFileName+"_"+DateUtil.getDate();
                }
            }
            return logConfig;
        }
    }


}
