package com.padcmyanmar.smtz.wechatredesign.data.models

import android.graphics.Bitmap
import com.padcmyanmar.smtz.wechatredesign.data.vos.*
import com.padcmyanmar.smtz.wechatredesign.network.FirestoreApi
import com.padcmyanmar.smtz.wechatredesign.network.RealtimeApi

interface UserModel {

    var mFirestoreApi: FirestoreApi
    var mRealtimeApi: RealtimeApi

    fun addUser(
        phone: String,
        password: String,
        userName: String,
        dateOfBirth: String,
        gender: String,
        userUID: String,
        profile: String
    )

    fun getUser(uid: String, onSuccess: (user: UserVO) -> Unit, onFailure: (String) -> Unit)

    fun getAllUsers(onSuccess: (List<UserVO>) -> Unit, onFailure: (String) -> Unit)

    fun uploadImageAndUpdateGrocery(user: UserVO, image : Bitmap)

    fun uploadPhotoToFirestoreAndReturnString(imageBitmap : Bitmap, onSuccess: (imageString: String) -> Unit)

    fun addMoment(
        millis: Long,
        likeCount: String,
        content: String,
        user: String,
        userName: String,
        userProfile: String,
        photoListString: ArrayList<String>
    )

    fun addLikedUserVO(
        millis: Long,
        moment: MomentVO,
        likedUser: String,
    )

    fun getMoments(onSuccess: (List<MomentVO>) -> Unit, onFailure: (String) -> Unit)

    fun getMomentsLikedByUser(momentLikedByLoggedInUser: Long, onSuccess: (List<LikedUserVO>) -> Unit, onFailure: (String) -> Unit)

    fun deleteLikedUserVO(likedUser: String, moment: MomentVO)

    fun addContactsEachOther(loggedInUser: UserVO, contact: UserVO)

    fun getContacts(loggedInUserUid: String, onSuccess: (List<UserVO>) -> Unit, onFailure: (String) -> Unit)

    fun addMessage(
        firstPersonUID: String,
        secondPersonUID: String,
        millis: Long,
        message: String,
        senderUID: String,
        senderName: String,
        senderProfile: String,
        file: List<String>,
    )

    fun getMessagesOfSpecificContact(
        firstPersonUID: String,
        secondPersonUID: String,
        onSuccess: (messages: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMessagedContactsOfCurrentUser(
        currentUser: String,
        onSuccess: (messagedContacts: List<String>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addGroup(
        groupName: String,
        members: List<String>
    )

    fun getAllGroups(
        onSuccess: (groups: List<GroupVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getGroup(
        groupName: String,
        onSuccess: (group: GroupVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addMessageToGroup(
        millis: Long,
        groupName: String,
        message: String,
        senderUID: String,
        senderName: String,
        senderProfile: String,
        file: List<String>,
    )

    fun getMessagesOfGroup(
        groupName: String,
        onSuccess: (messages: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    )
}