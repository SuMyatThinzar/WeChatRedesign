package com.padcmyanmar.smtz.wechatredesign.network

import android.graphics.Bitmap
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.MessageVO
import java.io.ByteArrayOutputStream
import java.util.*

object RealtimeDatabaseImpl : RealtimeApi {

    private val database: DatabaseReference = Firebase.database.reference

    private val storage = FirebaseStorage.getInstance()    // firebase storage
    private val storageReference = storage.reference

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
        database
            .child("contactsAndMessages")
            .child(firstPersonUID)
            .child(secondPersonUID)
            .child(millis.toString())      // key
            .setValue(MessageVO(millis, message, senderUID, senderName, senderProfile, file))
//            .addOnCompleteListener {  }  to handle success and failure of adding to database (one-time)
    }

    override fun getMessagesOfSpecificContact(
        firstPersonUID: String,
        secondPersonUID: String,
        onSuccess: (messages: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database
            .child("contactsAndMessages")
            .child(firstPersonUID)
            .child(secondPersonUID)  // key-value    value တွေကို loop ပတ်ပြီး ArrayList တစ်ခုထဲကို ပြန်ထည့်ပြီးသိမ်း
            .addValueEventListener(object : ValueEventListener {     // real-time  addListenerForSingleValueEvent = one-time

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val messageList = arrayListOf<MessageVO>()
                snapshot.children.forEach { dataSnapShot ->

                    dataSnapShot.getValue(MessageVO::class.java)?.let {
                        messageList.add(it)
                    }
                }
                onSuccess(messageList.reversed())
            }
        })
    }

    // loggedin user's contact List with all message List
    override fun getMessagedContactsOfCurrentUser(
        currentUser: String,
        onSuccess: (messagedContacts: List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database
            .child("contactsAndMessages")
            .child(currentUser)
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) { // snapshot -> size 2 contacts(uid only)

                    val contactList = arrayListOf<String>()
                    snapshot.children.forEach { dataSnapShot ->

                        dataSnapShot.key?.let { contactList.add(it) }

                    }
                    onSuccess(contactList)
                }
            })
    }

    override fun uploadPhotoListAndReturnStringList(
        imageBitmapList: List<Bitmap>,
        onSuccess: (imageStringList: List<String>) -> Unit
    ) {

        val imageStringList: MutableList<String> = mutableListOf()

        for (imageBitmap in imageBitmapList) {
            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()                   // Bitmap -> ByeArray

            val imageRef =
                storageReference.child("images/${UUID.randomUUID()}")   // သိမ်းမယ့်နေရာရဲ့ reference
            val uploadTask = imageRef.putBytes(data)

            uploadTask.continueWithTask {
                return@continueWithTask imageRef.downloadUrl   // Url
            }.addOnCompleteListener { task ->

                val imageUrl = task.result?.toString()
                imageUrl?.let {
                    imageStringList.add(imageUrl)

                    when(imageBitmapList.indexOf(imageBitmap)) {
                        imageBitmapList.lastIndex -> onSuccess(imageStringList)
                    }
                }
            }
        }
    }

    override fun addGroup(
        groupName: String,
        members: List<String>
    ) {
        database
            .child("groups")
            .child(groupName)      // key
            .setValue(GroupVO(groupName = groupName, members = members))
    }

    override fun getAllGroups(
        onSuccess: (groups: List<GroupVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database
            .child("groups")
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {   // "groups"->value/ {GroupOne,GroupTwo,GroupThree}

                    val groupList: MutableList<GroupVO> = mutableListOf()

                    snapshot.children.forEach { dataSnapShot ->  // GroupOne->value VO

                        dataSnapShot.getValue(GroupVO::class.java)?.let {
                            groupList.add(it)
                        }
                    }
                    onSuccess(groupList.reversed())
                }
            })
    }

    override fun getGroup(
        groupName: String,
        onSuccess: (group: GroupVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database
            .child("groups")
            .child(groupName)
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    snapshot.getValue(GroupVO::class.java)?.let{ onSuccess(it) }
                }
            })
    }

    override fun addMessageToGroup(
        millis: Long,
        groupName: String,
        message: String,
        senderUID: String,
        senderName: String,
        senderProfile: String,
        file: List<String>,
    ) {
        database
            .child("groups")    // groups->GroupOne->messages->millis->
            .child(groupName)
            .child("messages")
            .child(millis.toString())        // key
            .setValue(MessageVO(millis, message, senderUID, senderName, senderProfile, file))
    }

    override fun getMessagesOfGroup(
        groupName: String,
        onSuccess: (messages: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database
            .child("groups")
            .child(groupName)
            .child("messages")  //key-value     value တွေကို loop ပတ်ပြီး ArrayList တစ်ခုထဲကို ပြန်ထည့်ပြီးသိမ်း
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    val messageList = arrayListOf<MessageVO>()
                    snapshot.children.forEach { dataSnapShot ->

                        dataSnapShot.getValue(MessageVO::class.java)?.let {
                            messageList.add(it)
                        }
                    }
                    onSuccess(messageList.reversed())
                }
            })
    }
}