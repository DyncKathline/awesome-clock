package com.plain.awesome_clock.utils

import android.annotation.SuppressLint
import android.content.Context
import es.dmoral.toasty.Toasty

/**
 * Toast
 *
 * @author Plain
 * @date 2019-11-28 16:35
 */
object ToastUtils {

    fun showSuccessToast(context: Context, msg: String) {
        Toasty.success(context, msg).show()
    }

}