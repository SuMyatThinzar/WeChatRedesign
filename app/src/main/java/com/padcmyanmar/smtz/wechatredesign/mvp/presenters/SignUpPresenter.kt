package com.padcmyanmar.smtz.wechatredesign.mvp.presenters

import com.padc.grocery.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SignUpView

interface SignUpPresenter : BasePresenter<SignUpView> {
    fun onTapVerify(phone: String, code: String)
}