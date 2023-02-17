package com.padcmyanmar.smtz.wechatredesign.mvp.presenters

import android.graphics.Bitmap
import com.padc.grocery.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewMomentView

interface NewMomentPresenter : BasePresenter<NewMomentView> {
    fun onTapCreate(millis: Long, likeCount: String, content: String, user: String, userName: String, userProfile: String, photoListString: ArrayList<String>)
//    fun uploadPhotos(image: Bitmap)
}