package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.content.Context
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SignUpProfileView

interface SignUpProfilePresenter : BasePresenter<SignUpProfileView> {
    fun onTapSignUp(phone: String, name: String, password: String, day: String, month: String, year: String, gender: String, context: Context)
}