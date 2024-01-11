package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ChatThreadGroupView

interface ChatThreadGroupPresenter : BasePresenter<ChatThreadGroupView> {
    fun onUiReady(owner: LifecycleOwner, group: GroupVO)
    fun onTapSend(
        millis: Long,
        groupName: String,
        message: String,
        senderUID: String,
        senderName: String,
        senderProfile: String,
        file: List<Bitmap>
    )
}