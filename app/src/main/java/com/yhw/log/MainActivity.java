package com.yhw.log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yhw.loglib.Logger;
import com.yhw.loglib.http.UploadLog;

/**
 * demo
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logBtn = (Button) findViewById(R.id.log_btn);
        //打印
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.i("hello world!");
                Logger.i(TAG,"hello world 2!");
            }
        });

        Button logUploadBtn = (Button) findViewById(R.id.log_upload_btn);
        //上传日志
        logUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.upLoadLog(MainActivity.this, new UploadLog.UploadLogListener() {
                    @Override
                    public void onSuccess(String msg) {
                        Logger.i(msg);
                    }

                    @Override
                    public void onFailure(String errorMsg, int errorCode) {
                        Logger.e(errorMsg+" , "+errorCode);
                    }
                });
            }
        });
    }
}
