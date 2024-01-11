package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import androidx.lifecycle.ViewModel
import com.padcmyanmar.smtz.wechatredesign.mvp.views.BaseView

abstract class AbstractBasePresenter<T: BaseView> : BasePresenter<T>, ViewModel() {

    protected lateinit var mView : T
    override fun initPresenter(view: T){
        mView = view
    }
}