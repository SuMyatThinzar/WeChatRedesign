package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MyView

interface MyPresenter : BasePresenter<MyView> {
    fun onTapEditProfile(user: UserVO)
    fun onTapSaveProfile(user: UserVO)
    fun onPhotoTaken(bitmap: Bitmap, user: UserVO)
    fun onUiReady(owner: LifecycleOwner, uid: String)
    fun onTapLogout()
}