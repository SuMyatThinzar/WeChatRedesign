package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MainScreenView

class MainScreenPresenterImpl : MainScreenPresenter, AbstractBasePresenter<MainScreenView>() {

    private val mUserModel : UserModel = UserModelImpl

    override fun onUiReady(owner: LifecycleOwner, uid: String) {
        mUserModel.getUser(uid, onSuccess = {
            mView.setUpUserVO(it)
        }, onFailure = {
            mView.showError(it)
        })
    }

    override fun onUiReady(owner: LifecycleOwner) {}

}