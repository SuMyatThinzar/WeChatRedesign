package com.padcmyanmar.smtz.wechatredesign.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.AuthenticationModel
import com.padcmyanmar.smtz.wechatredesign.data.models.AuthenticationModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SignUpProfileView

class SignUpProfilePresenterImpl : SignUpProfilePresenter, AbstractBasePresenter<SignUpProfileView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    override fun onUiReady(owner: LifecycleOwner) {

    }

    override fun onTapSignUp(phone: String, name: String, password: String, day: String, month: String, year: String, gender: String) {
        val dateOfBirth = "$day/$month/$year"

        mAuthenticationModel.signUp(
            phone = phone,
            userName = name,
            password = password,
            dateOfBirth = dateOfBirth,
            gender = gender,
            onSuccess = { uid ->
                mView.showError("SignUp Successful")
                mView.navigateToMomentsScreen(uid)
            },
            onFailure = {
                mView.showError(it)
            }

        )
    }

}