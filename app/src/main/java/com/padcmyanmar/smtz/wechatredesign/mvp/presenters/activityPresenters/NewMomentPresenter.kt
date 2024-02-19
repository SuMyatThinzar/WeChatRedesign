package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.graphics.Bitmap
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewMomentView

interface NewMomentPresenter : BasePresenter<NewMomentView> {
    fun onTapCreate(millis: Long, likeCount: String, content: String, user: String, userName: String, userProfile: String, photoListBitmap: ArrayList<Bitmap>)
//    fun uploadPhotos(image: Bitmap)
}