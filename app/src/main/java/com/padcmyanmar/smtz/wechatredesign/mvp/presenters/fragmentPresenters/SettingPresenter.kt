package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import android.content.Context
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SettingView

interface SettingPresenter : BasePresenter<SettingView> {
    fun onTapLogout(context: Context)
}