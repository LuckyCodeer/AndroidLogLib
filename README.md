# AndroidLogLib
一个Android日志打印工具库，包含日志存储，日志上传功能

# Android Studio 集成
 ```java
implementation 'com.yhw.loglib:loglib:1.0.1'
 ```

# 代码中使用

1.在Application中初始化配置
 ```java
//初始化日志配置
new LogConfig.Builder()
        //设置日志打印开关 默认为true
        .setLog(true)
        //统一设置日志TAG 默认为LOG_TAG
        .setTAG("AAAAA")
        //设置日志是否保存到文件 默认为false
        .setSaveFile(true)
        //设置日志保存路径，在设置setSaveFile为true的情况下必须设置该路径
        .setLogPath(Environment.getExternalStorageDirectory().getPath())
        //设置日志名称 默认为当天的日期如2017-11-13，设置后变为log_2017-11-13
        .setLogFileName("log")
        //设置日志在客户端最大保存天数 默认为7天
        .setMaxSaveDay(7)
        //设置日志上传服务器地址 不上传的情况下可以不设置
        .setUploadUrl("http://xxx")
        .build();
 ```

2.日志打印
 ```java
如：
Logger.i("test");
或者
Logger.i("TAG","test");
 ```

3.日志上传
 ```java
//必须在初始化的时候设置服务器上传Url才可以
//使用方法
 Logger.upLoadLog(context, new UploadLog.UploadLogListener() {
        @Override
        public void onSuccess(String msg) {
            //处理成功
        }

        @Override
        public void onFailure(String errorMsg, int errorCode) {
            //处理失败
        }
});
 ```
