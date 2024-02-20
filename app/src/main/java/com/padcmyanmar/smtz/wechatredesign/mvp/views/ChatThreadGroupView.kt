package com.padcmyanmar.smtz.wechatredesign.mvp.views

import com.padcmyanmar.smtz.wechatredesign.data.vos.MessageVO

interface ChatThreadGroupView : BaseView {
    fun showGroupMessages(messages: List<MessageVO>)
    fun showLoadingView()
    fun hideLoadingView(isSuccess: Boolean)
}