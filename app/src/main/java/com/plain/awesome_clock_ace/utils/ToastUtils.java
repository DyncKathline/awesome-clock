package com.plain.awesome_clock_ace.utils;

import android.content.Context;
import es.dmoral.toasty.Toasty;

/**
 * Toast
 *
 * @author Plain
 * @date 2019-11-28 16:35
 */
public class ToastUtils {

    public static void showSuccessToast(Context context, String msg) {
        Toasty.success(context, msg).show();
    }

}