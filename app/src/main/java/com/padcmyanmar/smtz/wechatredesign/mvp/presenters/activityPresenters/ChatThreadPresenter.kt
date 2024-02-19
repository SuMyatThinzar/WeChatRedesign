package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ChatThreadView

interface ChatThreadPresenter : BasePresenter<ChatThreadView> {

    fun onUiReady(owner: LifecycleOwner, currentUser: String, contact: String)
    fun onTapSend(
        firstPersonUID: String,
        secondPersonUID: String,
        millis: Long,
        message: String,
        senderUID: String,
        senderName: String,
        senderProfile: String,
        file: List<Bitmap>
    )
}