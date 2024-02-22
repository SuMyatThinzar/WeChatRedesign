package com.padcmyanmar.smtz.wechatredesign.network

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.padcmyanmar.smtz.wechatredesign.data.vos.LikedUserVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import java.io.ByteArrayOutputStream
import java.util.*

object FirestoreDatabaseImpl : FirestoreApi {

    val db = Firebase.firestore  // cloud firestore

    private val storage = FirebaseStorage.getInstance()    // firebase storage
    private val storageReference = storage.reference

    override fun addUser(
        phone: String,
        password: String,
        userName: String,
        dateOfBirth: String,
        gender: String,
        userUID: String,
        profile: String
    ) {
        Log.d("sdfasfaf", "${userUID}")
        val userMap = hashMapOf(
            "userUID" to userUID,
            "name" to userName,
            "phone" to phone,
            "password" to password,
            "dateOfBirth" to dateOfBirth,
            "gender" to gender,
            "profile" to profile
        )

        db.collection("users")
            .document(userUID)  //key
            .set(userMap)
            .addOnSuccessListener { Log.d("Success", "Successfully added a user") }
            .addOnFailureListener { Log.d("Failure", "Failed to add user") }
    }

    override fun getUser(
        uid: String,
        onSuccess: (user: UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("users").document(uid)
            .addSnapshotListener { value, error ->

                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val user = UserVO()
                    user.name = value?.get("name") as String
                    user.phone = value["phone"] as String
                    user.password = value["password"] as String
                    user.dateOfBirth = value["dateOfBirth"] as String
                    user.gender = value["gender"] as String
                    user.userUID = value["userUID"] as String
                    user.profile = value["profile"] as String?
                    onSuccess(user)
                }
            }
    }

    override fun getAllUsers(onSuccess: (List<UserVO>) -> Unit, onFailure: (String) -> Unit) {
        db.collection("users")
            .addSnapshotListener { value, error ->

                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val userList: MutableList<UserVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()     // Document List

                    for (document in result) {
                        val data = document.data
                        val user = UserVO()

                        user.name = data?.get("name") as String
                        user.phone = data["phone"] as String
                        user.password = data["password"] as String
                        user.dateOfBirth = data["dateOfBirth"] as String
                        user.gender = data["gender"] as String
                        user.userUID = data["userUID"] as String
                        user.profile = data["profile"] as String?
                        userList.add(user)
                    }
                    onSuccess(userList)
                }
            }
    }

    override fun uploadProfileAndUpdateUser(image: Bitmap, user: UserVO) {

        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()                   // Bitmap -> ByeArray

        val imageRef =
            storageReference.child("images/${UUID.randomUUID()}")  // သိမ်းမယ့်နေရာရဲ့ reference
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener {
            //
        }.addOnSuccessListener {
            //
        }

        uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl   // Url
        }.addOnCompleteListener { task ->
            val imageUrl = task.result?.toString()         // task.result = Uri -> String
            addUser(
                userUID = user.userUID ?: "",
                userName = user.name ?: "",
                phone = user.phone ?: "",
                password = user.password ?: "",
                dateOfBirth = user.dateOfBirth ?: "",
                gender = user.gender ?: "",
                profile = imageUrl ?: ""
            )
        }
    }

    // Child photo list/ call this method with looping from newMomentActivity
    override fun uploadPhotoToFirestoreAndReturnString(
        imageBitmap: Bitmap,
        onSuccess: (imageString: String) -> Unit
    ) {

        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()                   // Bitmap -> ByeArray

        val imageRef = storageReference.child("images/${UUID.randomUUID()}")  // သိမ်းမယ့်နေရာရဲ့ reference
        val uploadTask = imageRef.putBytes(data)

        uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl   // Url
        }.addOnCompleteListener { task ->

            val imageUrl = task.result?.toString()
            imageUrl?.let {
                onSuccess(imageUrl)
            }
        }
    }

    override fun addMoment(
        millis: Long,
        likeCount: String,
        content: String,
        user: String,
        photoListString: ArrayList<String>,
        likedUsers: ArrayList<String>,
        bookmarkedUsers: ArrayList<String>,
        onCompletionListener: (isSuccess: Boolean, message: String) -> Unit,
    ) {

        val momentMap = hashMapOf(
            "millis" to millis,
            "likeCount" to likeCount,
            "content" to content,
            "user" to user,
            "photoList" to photoListString,
            "likedUsers" to likedUsers,
            "bookmarkedUsers" to bookmarkedUsers,
        )

        db.collection("moments")
            .document(millis.toString())  //key
            .set(momentMap)
            .addOnSuccessListener { onCompletionListener(true,"Moment Created") }
            .addOnFailureListener { onCompletionListener( false, it.message.toString()) }
    }

    override fun deleteMoment(
        momentId: Long,
        onCompletionListener: (isSuccess: Boolean, message: String) -> Unit
    ) {
        db.collection("moments")
            .document(momentId.toString())
            .delete()
            .addOnSuccessListener { onCompletionListener(true, "Moment Deleted") }
            .addOnFailureListener { onCompletionListener(false, it.message.toString()) }
    }

    override fun updateLikedUser(moment: MomentVO, likedUser: String) {

        addMoment(
            millis = moment.millis!!,
            likeCount = moment.likeCount ?: "0",
            content = moment.content ?: "",
            user = moment.user!!,
            photoListString = moment.photoList ?: arrayListOf(),
            likedUsers = moment.likedUsers ?: arrayListOf(),
            bookmarkedUsers = moment.bookmarkedUsers ?: arrayListOf()
        ) {_, _ ->}

        // သက်သက် document မှာ မသိမ်း‌တော့ဘူး
//        val likeMap = hashMapOf(
//            "millis" to millis,
//            "user" to likedUser,
//        )
//        db.collection("moments")
//            .document("${moment.millis}")
//            .collection("likedUsers")
//            .document(likedUser)  //key
//            .set(likeMap)
//            .addOnSuccessListener { Log.d("Success", "Successfully added like") }
//            .addOnFailureListener { Log.d("Failure", "Failed to add like") }
    }

//    override fun deleteLikedUserVO(likedUser: String, moment: MomentVO) {
//
//        if(moment.likeCount?.toInt()!! > 0) {
//            addMoment(
//                moment.millis!!,
//                (moment.likeCount?.toInt()!! - 1).toString(),
//                moment.content!!,
//                moment.user!!,
//                moment.photoList!!,
//                moment.likedUsers ?: arrayListOf()
//            ) {_, _ ->}
//        }
//        db.collection("moments")
//            .document("${moment.millis}")
//            .collection("likedUsers")
//            .document(likedUser)  //key
//            .delete()
//            .addOnSuccessListener { Log.d("Success", "Successfully deleted likedUser") }
//            .addOnFailureListener { Log.d("Failure", "Failed to delete likedUser") }
//    }

    override fun updateBookmarkedUser(moment: MomentVO) {

        addMoment(
            millis = moment.millis!!,
            likeCount = moment.likeCount?:"",
            content = moment.content?:"",
            user = moment.user?:"",
            photoListString = moment.photoList?: arrayListOf(),
            likedUsers = moment.likedUsers ?: arrayListOf(),
            bookmarkedUsers = moment.bookmarkedUsers ?: arrayListOf(),
        ) {_, _ ->}
    }

    // to bind like image of moments of logged in user
    override fun getMomentsLikedByUser(
        momentLikedByLoggedInUser: Long,
        onSuccess: (List<LikedUserVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("moments")
            .document(momentLikedByLoggedInUser.toString())
            .collection("likedUsers")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val likedUserList: MutableList<LikedUserVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()     // Document List

                    result.forEach { document ->
                        val data = document.data
                        val likedUser = LikedUserVO()
                        likedUser.millis = data?.get("millis") as Long
                        likedUser.userUID = data["user"] as String
                        likedUserList.add(likedUser)
                    }
                    onSuccess(likedUserList)
                }
            }
    }

    override fun getMoments(
        onSuccess: (List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("moments")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val momentList: MutableList<MomentVO> = arrayListOf()

                    val result = value?.documents ?: arrayListOf()     // Document List

                    result.forEach { document ->
                        val data = document.data
                        val moment = MomentVO()
                        moment.millis = data?.get("millis") as? Long
                        moment.likeCount = data?.get("likeCount") as? String
                        moment.content = data?.get("content") as? String
                        moment.user = data?.get("user") as? String
                        moment.photoList = data?.get("photoList") as? ArrayList<String>
                        moment.likedUsers = data?.get("likedUsers") as? ArrayList<String>
                        moment.bookmarkedUsers = data?.get("bookmarkedUsers") as? ArrayList<String>
                        momentList.add(moment)
                    }
                    onSuccess(momentList)
                }
            }
    }

    override fun addContactsEachOther(loggedInUser: UserVO, contact: UserVO) {
        addContacts(firstContact = contact, secondContact = loggedInUser)
        addContacts(firstContact = loggedInUser, secondContact = contact)
    }
    private fun addContacts(firstContact: UserVO, secondContact: UserVO) {
        val userMap = hashMapOf(
            "userUID" to firstContact.userUID,
            "name" to firstContact.name,
            "phone" to firstContact.phone,
            "password" to firstContact.password,
            "dateOfBirth" to firstContact.dateOfBirth,
            "gender" to firstContact.gender,
            "profile" to firstContact.profile
        )
        db.collection("users")
            .document(secondContact.userUID!!)
            .collection("contacts")
            .document(firstContact.userUID!!)       //key
            .set(userMap)
            .addOnSuccessListener { Log.d("Success", "Successfully added a user") }
            .addOnFailureListener { Log.d("Failure", "Failed to add user") }
    }

    override fun getContacts(
        loggedInUserUid: String,
        onSuccess: (List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("users")
            .document(loggedInUserUid)
            .collection("contacts")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {

                    val contactList: MutableList<UserVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()     // Document List

                    for (document in result) {
                        val data = document.data
                        val user = UserVO()

                        user.name = data?.get("name") as String
                        user.phone = data["phone"] as String
                        user.password = data["password"] as String
                        user.dateOfBirth = data["dateOfBirth"] as String
                        user.gender = data["gender"] as String
                        user.userUID = data["userUID"] as String
                        user.profile = data["profile"] as String?
                        contactList.add(user)
                    }
                    onSuccess(contactList.toList())
                }
            }
    }
}