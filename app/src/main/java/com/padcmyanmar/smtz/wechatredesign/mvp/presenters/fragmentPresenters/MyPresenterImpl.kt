package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MyView

class MyPresenterImpl : MyPresenter, AbstractBasePresenter<MyView>() {

    override fun onUiReady(owner: LifecycleOwner, uid: String) {
//        mUserModel.getUser(uid, onSuccess = {
//            mView.setUpUserVO(it)
//        }, onFailure = {
//            mView.showError(it)
//        })
    }

    override fun onTapEditProfile(user: UserVO) {
        mView.showDialog(user)
    }

    override fun onTapSaveProfile(user: UserVO) {
        mUserModel.addUser(user.phone?:"", user.password?:"", user.name?:"", user.dateOfBirth?:"", user.gender?:"", user.userUID?:"", user.profile?:"")
    }

    override fun onPhotoTaken(bitmap: Bitmap, user: UserVO) {
        mUserModel.uploadImageAndUpdateGrocery(user, bitmap)
    }

//    override fun onTapLogout() {
//        mView.navigateToLoginView()
//    }

    override fun onUiReady(owner: LifecycleOwner) {}

}