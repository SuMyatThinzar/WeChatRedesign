package com.padcmyanmar.smtz.wechatredesign.mvp.presenters

import com.padc.grocery.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SplashScreenView

interface SplashPresenter : BasePresenter<SplashScreenView> {
    fun onTapSignUp()
    fun onTapLogin()
}