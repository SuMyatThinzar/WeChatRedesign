package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ChatThreadView
import com.padcmyanmar.smtz.wechatredesign.network.RealtimeApi
import com.padcmyanmar.smtz.wechatredesign.network.RealtimeDatabaseImpl

class ChatThreadPresenterImpl : ChatThreadPresenter, AbstractBasePresenter<ChatThreadView>() {

    private var mRealtimeApi: RealtimeApi = RealtimeDatabaseImpl

    override fun onUiReady(owner: LifecycleOwner, currentUser: String, contact: String) {
        mUserModel.getUser(currentUser, onSuccess = {
            mView.setUpCurrentUserVO(it)
        }, onFailure = {
            mView.showError(it)
        })
        mUserModel.getUser(contact, onSuccess = {
            mView.setUpContactVO(it)
        }, onFailure = {
            mView.showError(it)
        })
        mUserModel.getMessagesOfSpecificContact(firstPersonUID = currentUser, secondPersonUID = contact, onSuccess = {
            mView.showMessages(it)
        }, onFailure = {
            mView.showError(it)
        })
    }

    override fun onTapSend(firstPersonUID: String, secondPersonUID: String, millis: Long, message: String, senderUID: String, senderName: String, senderProfile: String, file: List<Bitmap>) {
        addImage(firstPersonUID, secondPersonUID, millis, message, senderUID, senderName, senderProfile, emptyList())

        if (file.isNotEmpty()){
            mRealtimeApi.uploadPhotoListAndReturnStringList(file, onSuccess = {
                addImage(firstPersonUID, secondPersonUID, millis, message, senderUID, senderName, senderProfile,
                    it)
            })
        }
    }

    private fun addImage(firstPersonUID: String, secondPersonUID: String, millis: Long, message: String, senderUID: String, senderName: String, senderProfile: String, file: List<String>) {
        mUserModel.addMessage(
            firstPersonUID = firstPersonUID,
            secondPersonUID = secondPersonUID,
            millis = millis,
            message = message,
            senderUID = senderUID,
            senderName = senderName,
            senderProfile = senderProfile,
            file = file
        )
        mUserModel.addMessage(
            firstPersonUID = secondPersonUID,
            secondPersonUID = firstPersonUID,
            millis = millis,
            message = message,
            senderUID = senderUID,
            senderName = senderName,
            senderProfile = senderProfile,
            file = file
        )
    }

    override fun onUiReady(owner: LifecycleOwner) {}

}