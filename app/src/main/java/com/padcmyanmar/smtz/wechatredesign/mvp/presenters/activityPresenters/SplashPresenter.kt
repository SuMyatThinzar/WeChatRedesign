package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import com.padcmyanmar.smtz.wechatredesign.mvp.views.SplashScreenView

interface SplashPresenter : BasePresenter<SplashScreenView> {
    fun onTapSignUp()
    fun onTapLogin()
}