package com.yhw.loglib.utils;

import android.os.Environment;

/**
 * Author: yhw on 2017-11-10.
 */

public final class LogUtil {

    /**
     * 是否存在SD卡
     */
    public static boolean isExistsSdcard(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
