package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.AuthenticationModel
import com.padcmyanmar.smtz.wechatredesign.data.models.AuthenticationModelImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SignUpView

class SignUpPresenterImpl : SignUpPresenter, AbstractBasePresenter<SignUpView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    override fun onUiReady(owner: LifecycleOwner) {


    }
    override fun onTapVerify(phone: String, code: String) {
        mAuthenticationModel.verify(
            phone = phone,
            code = code,
            onSuccess = { mView.navigateToSignUpProfileScreen(phone) },
            onFailure = { mView.showError(it) }
        )

    }

}