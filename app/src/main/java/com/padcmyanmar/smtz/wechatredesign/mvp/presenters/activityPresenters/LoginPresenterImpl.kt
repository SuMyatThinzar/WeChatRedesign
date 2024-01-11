package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.AuthenticationModel
import com.padcmyanmar.smtz.wechatredesign.data.models.AuthenticationModelImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.LoginView
import com.padcmyanmar.smtz.wechatredesign.utils.customPrefs
import com.padcmyanmar.smtz.wechatredesign.utils.set

class LoginPresenterImpl : LoginPresenter, AbstractBasePresenter<LoginView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    override fun onUiReady(owner: LifecycleOwner) {

    }

    override fun onTapLogin(phone: String, password: String, context: Context) {

        mAuthenticationModel.login(phone, password, onSuccess = { uid ->

            //save to SharedPreference
            val preference = customPrefs(context, "user_login")
            preference.set("uid", uid)

            mView.navigateToMomentView(uid)
        }, onFailure = {
            mView.showError(it)
        })
    }
}