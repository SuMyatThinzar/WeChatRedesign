package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.AuthenticationModel
import com.padcmyanmar.smtz.wechatredesign.data.models.AuthenticationModelImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SignUpProfileView
import com.padcmyanmar.smtz.wechatredesign.utils.customPrefs
import com.padcmyanmar.smtz.wechatredesign.utils.set

class SignUpProfilePresenterImpl : SignUpProfilePresenter, AbstractBasePresenter<SignUpProfileView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    override fun onUiReady(owner: LifecycleOwner) {

    }

    override fun onTapSignUp(phone: String, name: String, password: String, day: String, month: String, year: String, gender: String, context: Context) {
        val dateOfBirth = "$day/$month/$year"

        mAuthenticationModel.signUp(
            phone = phone,
            userName = name,
            password = password,
            dateOfBirth = dateOfBirth,
            gender = gender,
            onSuccess = { uid ->
                //save to SharedPreference
                val preference = customPrefs(context, "user_login")
                preference.set("uid", uid)

                mView.showError("SignUp Successful")

                mView.navigateToMomentsScreen(uid)
            },
            onFailure = {
                mView.showError(it)
            }

        )
    }

}