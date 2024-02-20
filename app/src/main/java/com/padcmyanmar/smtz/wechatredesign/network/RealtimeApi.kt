package com.padcmyanmar.smtz.wechatredesign.network

import android.graphics.Bitmap
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.MessageVO

interface RealtimeApi {
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

    fun uploadPhotoListAndReturnStringList(
        imageBitmapList: List<Bitmap>,
        onSuccess: (imageStringList: List<String>) -> Unit
    )

    fun addGroup(
        groupName: String,
        members: List<String>
    )

    fun deleteGroup(
        groupName: String,
        onCompleteListener: (Boolean, String) -> Unit
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