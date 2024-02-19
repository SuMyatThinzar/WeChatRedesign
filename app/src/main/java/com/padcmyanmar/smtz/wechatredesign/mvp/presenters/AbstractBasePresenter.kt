package com.padcmyanmar.smtz.wechatredesign.mvp.presenters

import androidx.lifecycle.ViewModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.BaseView

abstract class AbstractBasePresenter<T: BaseView> : BasePresenter<T>, ViewModel() {

    protected val mUserModel: UserModel by lazy { UserModelImpl }

    protected lateinit var mView : T
    override fun initPresenter(view: T){
        mView = view
    }
}