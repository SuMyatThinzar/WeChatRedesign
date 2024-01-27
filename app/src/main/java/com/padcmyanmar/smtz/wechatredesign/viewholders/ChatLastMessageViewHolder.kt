package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.ChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.utils.timestampToDateString
import com.padcmyanmar.smtz.wechatredesign.utils.timestampToTimeString
import kotlinx.android.synthetic.main.view_holder_chat.view.*

class ChatLastMessageViewHolder(itemView: View, private val mDelegate: ChatThreadDelegate, private var currentUser: String) :
    RecyclerView.ViewHolder(itemView) {

    private var mContact: String? = null
    private var mUserModel: UserModel = UserModelImpl

    init {
        itemView.setOnClickListener {
            mContact?.let { contact ->
                mDelegate.onTapChat(currentUser, contact)
            }
        }
    }

    fun bindData(contactsWithSpecificMessages: String) {

        mUserModel.getUser(contactsWithSpecificMessages, onSuccess = {
            mContact = it.userUID

            Glide.with(itemView.context)
                .load(it.profile)
                .into(itemView.ivContactOrGroupProfile)
            itemView.tvContactOrGroupName.text = it.name
        }, onFailure = {})

        mUserModel.getMessagesOfSpecificContact(
            firstPersonUID = currentUser,
            secondPersonUID = contactsWithSpecificMessages,
            onSuccess = { messageList ->

                messageList.sortedBy { it.millis }

                val currentTime = System.currentTimeMillis()
                val messagedTime = messageList.firstOrNull()?.millis ?: 0L

                // last chatted time is less than 24 hours
                if (currentTime - messagedTime <= 86400000L) {
                    itemView.tvLastChattedTime.text = timestampToTimeString(messagedTime)  //1:30 AM
                } else {
                    itemView.tvLastChattedTime.text = timestampToDateString(messagedTime)  //21.3.2024
                }

                if(messageList.firstOrNull()?.message != ""){
                    itemView.tvLastMessage.text = messageList.firstOrNull()?.message
                } else {
                    itemView.tvLastMessage.text = "sent a photo"
                }
            },
            onFailure = {}
        )
    }
}