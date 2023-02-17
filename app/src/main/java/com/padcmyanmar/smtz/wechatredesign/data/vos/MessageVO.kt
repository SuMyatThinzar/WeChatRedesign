package com.padcmyanmar.smtz.wechatredesign.data.vos

data class MessageVO (
    val millis: Long? = null,
    val message: String? = null,
    val senderUID: String? = null,
    val senderName: String? = null,
    val senderProfile: String? = null,
    val file: List<String>? = listOf()
        )