package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewMomentView

class NewMomentPresenterImpl : NewMomentPresenter, AbstractBasePresenter<NewMomentView>() {

    override fun onTapCreate(
        millis: Long, likeCount: String, content: String, user: String, userName: String, userProfile: String,
        photoListBitmap: ArrayList<Bitmap>
    ) {
        val moment = MomentVO(
            millis = millis,
            likeCount = likeCount,
            content = content,
            user = user
        )

        mView.showProgressBar()
        val photoStringList = mutableListOf<String>()

        // photo is included or not
        if (photoListBitmap.isEmpty()) {
            networkCallAddMoment(moment)
        } else {
            photoListBitmap.forEachIndexed { index, photoBitmap ->
                mUserModel.uploadPhotoToFirestoreAndReturnString(photoBitmap) { photoString ->
                    photoStringList.add(photoString)
                    if (index == photoListBitmap.lastIndex) {

                        moment.photoList = photoStringList as ArrayList
                        networkCallAddMoment(moment)
                    }
                }
            }
        }
    }

    private fun networkCallAddMoment(moment: MomentVO) {
        mUserModel.addMoment(
            moment.millis?:0,
            moment.likeCount?:"0",
            moment.content?:"",
            moment.user?:"",
            moment.photoList ?: arrayListOf()
        ) { isSuccess, message ->

        mView.showError(message)
            mView.hideProgressBar()
            if (isSuccess) mView.finishActivity()
        }
    }

    override fun onUiReady(owner: LifecycleOwner) {}

}