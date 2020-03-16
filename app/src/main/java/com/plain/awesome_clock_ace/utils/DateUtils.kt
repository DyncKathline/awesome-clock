package com.plain.awesome_clock_ace.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 *  name: DateUtils
 *  desc: TODO
 *  date: 2020/3/16 4:53 PM
 *  version: v1.0
 *  author: Plain
 *  blog: https://plain-dev.com
 *  email: support@plain-dev.com
 */
object DateUtils {

    fun getHour(): String {
        val date = Date()
        val dateFormat = SimpleDateFormat("HH", Locale.getDefault())
        return dateFormat.format(date)
    }

}