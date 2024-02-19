package com.padcmyanmar.smtz.wechatredesign.network

import android.graphics.Bitmap
import com.padcmyanmar.smtz.wechatredesign.data.vos.LikedUserVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface FirestoreApi {
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

    fun uploadProfileAndUpdateUser(image : Bitmap, user: UserVO)

    fun uploadPhotoToFirestoreAndReturnString(imageBitmap : Bitmap, onSuccess: (imageString: String) -> Unit)

    fun addMoment(
        millis: Long,
        likeCount: String,
        content: String,
        user: String,
        photoListString: ArrayList<String>,
        likedUsers: ArrayList<String>,
        bookmarkedUsers: java.util.ArrayList<String>,
        onCompletionListener: (isSuccess: Boolean, message: String) -> Unit,
    )

    fun deleteMoment(
        momentId: Long,
        onCompletionListener: (isSuccess: Boolean, message: String) -> Unit,
    )

    fun getMoments(onSuccess: (List<MomentVO>) -> Unit, onFailure: (String) -> Unit)

    fun getMomentsLikedByUser(momentLikedByLoggedInUser: Long, onSuccess: (List<LikedUserVO>) -> Unit, onFailure: (String) -> Unit)

    fun updateLikedUser(moment: MomentVO, likedUser: String)

    fun updateBookmarkedUser(moment: MomentVO)

    fun addContactsEachOther(loggedInUser: UserVO, contact: UserVO)

    fun getContacts(loggedInUserUid: String, onSuccess: (List<UserVO>) -> Unit, onFailure: (String) -> Unit)


}