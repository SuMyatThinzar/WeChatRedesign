package com.padcmyanmar.smtz.wechatredesign.mvp.views

interface LoginView : BaseView {
    fun navigateToMomentView(uid: String)
    fun showProgressBar()
    fun hideProgeessBar()
}