package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MainScreenView

interface MainScreenPresenter : BasePresenter<MainScreenView> {
    fun onUiReady(owner: LifecycleOwner, uid: String)
}