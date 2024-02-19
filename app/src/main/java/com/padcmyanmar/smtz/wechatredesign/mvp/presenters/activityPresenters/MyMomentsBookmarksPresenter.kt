package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.MomentActionButtonsDelegate
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MyMomentsBookmarksView

interface MyMomentsBookmarksPresenter : BasePresenter<MyMomentsBookmarksView>,
    MomentActionButtonsDelegate {
    fun onUiReady(owner: LifecycleOwner, currentUser: UserVO, type: String)
}