package com.padcmyanmar.smtz.wechatredesign.mvp.presenters

import android.content.Context
import com.padc.grocery.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.LoginView

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapLogin(phone: String, password: String, context: Context)
}