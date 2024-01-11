package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.content.Context
import com.padcmyanmar.smtz.wechatredesign.mvp.views.LoginView

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapLogin(phone: String, password: String, context: Context)
}