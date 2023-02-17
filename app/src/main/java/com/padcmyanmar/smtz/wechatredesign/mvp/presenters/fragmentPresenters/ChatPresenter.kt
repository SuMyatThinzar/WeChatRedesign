package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.delegates.ChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.delegates.GroupToChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ChatView

interface ChatPresenter : BasePresenter<ChatView>, ChatThreadDelegate, GroupToChatThreadDelegate {
    fun onUiReady(owner: LifecycleOwner, loggedInUserUID: String)
}