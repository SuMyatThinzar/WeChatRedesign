package com.padcmyanmar.smtz.wechatredesign.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.ChatThreadDelegate
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

        mContact = contactsWithSpecificMessages

        mUserModel.getMessagesOfSpecificContact(
            firstPersonUID = currentUser,
            secondPersonUID = contactsWithSpecificMessages,
            onSuccess = { messageList ->

                messageList.sortedBy { it.millis }
                if(messageList.firstOrNull()?.message != ""){
                    itemView.tvLastMessage.text = messageList.firstOrNull()?.message
                } else {
                    itemView.tvLastMessage.text = "sent a photo"
                }
            },
            onFailure = {}
        )
        mUserModel.getUser(contactsWithSpecificMessages, onSuccess = {

            Glide.with(itemView.context)
                .load(it.profile)
                .into(itemView.ivContactOrGroupProfile)

            itemView.tvContactOrGroupName.text = it.name

        }, onFailure = {})

    }
}