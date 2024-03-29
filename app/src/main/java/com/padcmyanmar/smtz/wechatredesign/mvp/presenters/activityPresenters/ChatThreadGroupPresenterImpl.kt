package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ChatThreadGroupView
import com.padcmyanmar.smtz.wechatredesign.network.RealtimeApi
import com.padcmyanmar.smtz.wechatredesign.network.RealtimeDatabaseImpl

class ChatThreadGroupPresenterImpl : ChatThreadGroupPresenter, AbstractBasePresenter<ChatThreadGroupView>() {

    private var mRealtimeApi: RealtimeApi = RealtimeDatabaseImpl

    override fun onUiReady(owner: LifecycleOwner, groupVO: GroupVO) {
        mUserModel.getMessagesOfGroup(groupVO.groupName, onSuccess = { messages->
            mView.showGroupMessages(messages)
        }, onFailure = { mView.showError(it)} )
    }

    override fun onTapSend(millis: Long, groupName: String, message: String, senderUID: String, senderName: String, senderProfile: String, file: List<Bitmap>) {
        addImage(millis, groupName, message, senderUID, senderName, senderProfile, emptyList())

        if (file.isNotEmpty()){
            mRealtimeApi.uploadPhotoListAndReturnStringList(file, onSuccess = {
                addImage(millis, groupName, message, senderUID, senderName, senderProfile, it)
            })
        }
    }

    private fun addImage(millis: Long, groupName: String, message: String, senderUID: String, senderName: String, senderProfile: String, file: List<String>) {
        mUserModel.addMessageToGroup(
            millis = millis,
            groupName = groupName,
            message = message,
            senderUID = senderUID,
            senderName = senderName,
            senderProfile = senderProfile,
            file = file
        )
    }

    override fun onTapDelete(name: String) {
        mView.showLoadingView()
        mUserModel.deleteGroup(name) { isSuccess, message ->
            mView.showError(message)
            mView.hideLoadingView(isSuccess)
        }
    }

    override fun onUiReady(owner: LifecycleOwner) {}

}