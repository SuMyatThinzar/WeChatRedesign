package com.padcmyanmar.smtz.wechatredesign.data.models

import android.graphics.Bitmap
import com.padcmyanmar.smtz.wechatredesign.data.vos.*
import com.padcmyanmar.smtz.wechatredesign.network.FirestoreApi
import com.padcmyanmar.smtz.wechatredesign.network.FirestoreDatabaseImpl
import com.padcmyanmar.smtz.wechatredesign.network.RealtimeApi
import com.padcmyanmar.smtz.wechatredesign.network.RealtimeDatabaseImpl

object UserModelImpl : UserModel {

    override var mFirestoreApi: FirestoreApi = FirestoreDatabaseImpl
    override var mRealtimeApi: RealtimeApi = RealtimeDatabaseImpl

    override fun getUser(uid: String, onSuccess: (user: UserVO) -> Unit, onFailure: (String) -> Unit) {
        mFirestoreApi.getUser(uid, onSuccess, onFailure)
    }

    override fun getAllUsers(onSuccess: (List<UserVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirestoreApi.getAllUsers(onSuccess, onFailure)
    }

    override fun addUser(
        phone: String,
        password: String,
        userName: String,
        dateOfBirth: String,
        gender: String,
        userUID: String,
        profile: String
    ) {
        mFirestoreApi.addUser(phone, password, userName, dateOfBirth, gender, userUID, profile)
    }

    override fun uploadImageAndUpdateGrocery(user: UserVO, image: Bitmap) {
        mFirestoreApi.uploadProfileAndUpdateUser(image, user)
    }

    override fun uploadPhotoToFirestoreAndReturnString(imageBitmap: Bitmap, onSuccess: (imageString: String) -> Unit) {
        mFirestoreApi.uploadPhotoToFirestoreAndReturnString(imageBitmap, onSuccess)
    }

    override fun addMoment(
        millis: Long,
        likeCount: String,
        content: String,
        user: String,
        photoListString: ArrayList<String>,
        onCompletionListener: (isSuccess: Boolean, message: String) -> Unit
    ) {
        mFirestoreApi.addMoment(millis, likeCount, content, user, photoListString, likedUsers = arrayListOf(), bookmarkedUsers = arrayListOf(), onCompletionListener)
    }

    override fun deleteMoment(
        momentId: Long,
        onCompletionListener: (isSuccess: Boolean, message: String) -> Unit
    ) {
        mFirestoreApi.deleteMoment(momentId, onCompletionListener)
    }

    override fun updateLikedUser(moment: MomentVO, likedUser: String) {
        mFirestoreApi.updateLikedUser(moment, likedUser)
    }

    override fun updateBookmarkedUser(moment: MomentVO) {
        mFirestoreApi.updateBookmarkedUser(moment)
    }

    override fun getMoments(onSuccess: (List<MomentVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirestoreApi.getMoments(onSuccess, onFailure)
    }

    override fun getMomentsLikedByUser(momentLikedByLoggedInUser: Long, onSuccess: (List<LikedUserVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirestoreApi.getMomentsLikedByUser(momentLikedByLoggedInUser, onSuccess, onFailure)
    }


    override fun addContactsEachOther(loggedInUser: UserVO, contact: UserVO) {
        mFirestoreApi.addContactsEachOther(loggedInUser, contact)
    }

    override fun getContacts(loggedInUserUid: String, onSuccess: (List<UserVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirestoreApi.getContacts(loggedInUserUid, onSuccess, onFailure)
    }

    //
    override fun addMessage(
        firstPersonUID: String,
        secondPersonUID: String,
        millis: Long,
        message: String,
        senderUID: String,
        senderName: String,
        senderProfile: String,
        file: List<String>,
    ) {
        mRealtimeApi.addMessage(firstPersonUID, secondPersonUID, millis, message, senderUID, senderName, senderProfile, file)
    }

    override fun getMessagesOfSpecificContact(firstPersonUID: String, secondPersonUID: String, onSuccess: (messages: List<MessageVO>) -> Unit, onFailure: (String) -> Unit) {
        mRealtimeApi.getMessagesOfSpecificContact(firstPersonUID, secondPersonUID, onSuccess, onFailure)
    }

    override fun getMessagedContactsOfCurrentUser(currentUser: String, onSuccess: (messagedContacts: List<String>) -> Unit, onFailure: (String) -> Unit) {
        mRealtimeApi.getMessagedContactsOfCurrentUser(currentUser, onSuccess, onFailure)
    }

    override fun addGroup(groupName: String, members: List<String>) {
        mRealtimeApi.addGroup(groupName, members)
    }

    override fun getAllGroups(
        onSuccess: (groups: List<GroupVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mRealtimeApi.getAllGroups(onSuccess, onFailure)
    }

    override fun getGroup(
        groupName: String,
        onSuccess: (group: GroupVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mRealtimeApi.getGroup(groupName, onSuccess, onFailure)
    }

    override fun addMessageToGroup(
        millis: Long,
        groupName: String,
        message: String,
        senderUID: String,
        senderName: String,
        senderProfile: String,
        file: List<String>
    ) {
        mRealtimeApi.addMessageToGroup(millis, groupName, message, senderUID, senderName, senderProfile, file)
    }

    override fun getMessagesOfGroup(
        groupName: String,
        onSuccess: (messages: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mRealtimeApi.getMessagesOfGroup(groupName, onSuccess, onFailure)
    }
}