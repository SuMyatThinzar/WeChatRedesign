package com.padcmyanmar.smtz.wechatredesign.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun timestampToTimeString(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())  // 12:30 AM
    val date = Date(timestamp)
    return dateFormat.format(date)
}

fun timestampToDateString(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("d.M.yyyy", Locale.getDefault())  //21.3.2024
    val date = Date(timestamp)
    return dateFormat.format(date)
}