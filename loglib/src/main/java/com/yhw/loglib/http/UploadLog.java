package com.yhw.loglib.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yhw.loglib.utils.ZipUtil;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

/**
 * 上传日志到服务器
 * Author: yhw on 2017-11-13.
 */

public final class UploadLog {
    private static final String TAG = UploadLog.class.getSimpleName();
    private String mLogUrl; //上传服务器地址
    private String mLogPath; //日志路径
    private Context mContext;
    private UploadLogListener mUploadLogListener;
    private Handler mHandler = new Handler();

    public UploadLog(Context context,UploadLogListener uploadLogListener){
        this.mContext = context;
        this.mUploadLogListener = uploadLogListener;
    }

    /**
     * 上传日志
     * 自动压缩
     */
    public void upload(){
        final ZipUtil zipUtil = new ZipUtil();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG,"mLogPath: "+mLogPath);
                    File file = new File(mLogPath);
                    String logZipPath = file.getParent()+File.separator+"log.zip";
                    zipUtil.setZipListener(new ZipUtil.ZIPListener() {
                        @Override
                        public void zipSuccess(final String outPath) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    upload(outPath);
                                }
                            });
                        }
                    });
                    zipUtil.zip(mLogPath,logZipPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void upload(String logPath) {
        File logFile = new File(logPath);
        if(!logFile.exists()){
            return;
        }
        try {
            AsyncHttpClient httpClient = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("logFile",logFile);
            httpClient.post(mContext, mLogUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        if(mUploadLogListener!=null){
                            if(responseBody==null){
                                mUploadLogListener.onFailure("上传日志发生错误！",statusCode);
                                return;
                            }
                            String msg =new String(responseBody);
                            if(statusCode==200){
                                mUploadLogListener.onSuccess(msg);
                            }else{
                                mUploadLogListener.onFailure(msg,statusCode);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    try {
                        if(mUploadLogListener!=null){
                            if(responseBody==null){
                                mUploadLogListener.onFailure("上传日志发生错误！",statusCode);
                                return;
                            }
                            String msg =new String(responseBody);
                            mUploadLogListener.onFailure(msg,statusCode);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface UploadLogListener{
        void onSuccess(String msg);
        void onFailure(String errorMsg, int errorCode);
    }

    public void setLogUrl(String logUrl) {
        this.mLogUrl = logUrl;
    }

    public void setLogPath(String logPath) {
        this.mLogPath = logPath;
    }
}
