package com.padcmyanmar.smtz.wechatredesign.network

import android.graphics.Bitmap
import com.padcmyanmar.smtz.wechatredesign.data.vos.LikedUserVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import kotlinx.coroutines.flow.Flow

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
        userName: String,
        userProfile: String,
        photoListString: ArrayList<String>,
        likedUsers: ArrayList<String>
    )

    fun getMoments(onSuccess: (List<MomentVO>) -> Unit, onFailure: (String) -> Unit)

    fun getMomentsLikedByUser(momentLikedByLoggedInUser: Long, onSuccess: (List<LikedUserVO>) -> Unit, onFailure: (String) -> Unit)

    fun addLikedUserVO(millis: Long, moment: MomentVO, likedUser: String)

    fun deleteLikedUserVO(likedUser: String, moment: MomentVO)

    fun addContactsEachOther(loggedInUser: UserVO, contact: UserVO)

    fun getContacts(loggedInUserUid: String, onSuccess: (List<UserVO>) -> Unit, onFailure: (String) -> Unit)


}