package com.padcmyanmar.smtz.wechatredesign.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MainScreenView

interface MainScreenPresenter : BasePresenter<MainScreenView> {
    fun onUiReady(owner: LifecycleOwner, uid: String)
}