package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SplashScreenView

class SplashPresenterImpl : SplashPresenter, AbstractBasePresenter<SplashScreenView>() {

    override fun onUiReady(owner: LifecycleOwner) {

    }

    override fun onTapSignUp() {
        mView.navigateToSignUpScreen()
    }

    override fun onTapLogin() {
        mView.navigateToLoginScreen()
    }
}