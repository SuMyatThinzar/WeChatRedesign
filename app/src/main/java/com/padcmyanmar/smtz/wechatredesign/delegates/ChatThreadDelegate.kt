package com.padcmyanmar.smtz.wechatredesign.delegates

interface ChatThreadDelegate {
    fun onTapChat(loggedInUserUID: String, contactUID: String)
}