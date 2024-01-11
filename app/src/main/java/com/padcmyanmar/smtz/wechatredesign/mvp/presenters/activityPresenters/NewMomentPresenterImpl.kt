package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewMomentView

class NewMomentPresenterImpl : NewMomentPresenter, AbstractBasePresenter<NewMomentView>() {

    private var mUserModel: UserModel = UserModelImpl

    override fun onTapCreate(millis: Long, likeCount: String, content: String, user: String, userName: String, userProfile: String, photoListString: ArrayList<String>) {
        mUserModel.addMoment(millis, likeCount, content, user, userName, userProfile, photoListString)
    }

//    override fun uploadPhotos(image:Bitmap){
//        mUserModel.uploadPhotoToFirestoreAndReturnString(
//            image,
//            onSuccess = {
//                mView.setUpImageStringArray(it)
//        })
//    }

    override fun onUiReady(owner: LifecycleOwner) {

    }

}