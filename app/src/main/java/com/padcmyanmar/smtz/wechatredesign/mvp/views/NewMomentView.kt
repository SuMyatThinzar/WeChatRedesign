package com.padcmyanmar.smtz.wechatredesign.mvp.views

interface NewMomentView : BaseView {
    fun setUpImageStringArray(imageString: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun finishActivity()
}