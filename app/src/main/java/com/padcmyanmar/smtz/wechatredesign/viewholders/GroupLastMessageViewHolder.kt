package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.GroupToChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.utils.timestampToDateString
import com.padcmyanmar.smtz.wechatredesign.utils.timestampToTimeString
import kotlinx.android.synthetic.main.view_holder_chat.view.*
import kotlinx.android.synthetic.main.view_holder_messages.view.tvTimeSent


class GroupLastMessageViewHolder(itemView: View, private val mDelegate: GroupToChatThreadDelegate, private var currentUser: UserVO) : RecyclerView.ViewHolder(itemView) {

    private var mDataVO : GroupVO? = null
    private var mUserModel: UserModel = UserModelImpl

    init {
        itemView.setOnClickListener {
            mDataVO?.let {
                mDelegate.onTapGroup(currentUser, it)
            }
        }
    }

    fun bindData(data: String) {
        mUserModel.getGroup(data, onSuccess = {
            mDataVO = it
        }, onFailure = {})

        mUserModel.getMessagesOfGroup(data, onSuccess = { messageList->

            if(messageList.isNotEmpty()) {          // message history shi mha
                messageList.sortedBy { it.millis }  // sort time from last to first
                itemView.tvContactOrGroupName.text = data
                
                val currentTime = System.currentTimeMillis()
                val messagedTime = messageList.firstOrNull()?.millis ?: 0L

                // last chatted time is less than 24 hours
                if (currentTime - messagedTime <= 86400000L) {
                    itemView.tvLastChattedTime.text = timestampToTimeString(messagedTime)  //1:30 AM
                } else {
                    itemView.tvLastChattedTime.text = timestampToDateString(messagedTime)  //21.3.2024
                }

                if(messageList.firstOrNull()?.message != "") {
                    itemView.tvLastMessage.text = messageList.firstOrNull()?.message
                } else {
                    itemView.tvLastMessage.text = "sent a photo"
                }
            } else {
                itemView.tvLastMessage.text = "Group Created"
            }
        }, onFailure = {})
    }
}