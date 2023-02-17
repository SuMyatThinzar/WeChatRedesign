package com.padcmyanmar.smtz.wechatredesign.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.GroupToChatThreadDelegate
import kotlinx.android.synthetic.main.view_holder_chat.view.*


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


//        Glide.with(itemView.context)
//            .load(data.)
//            .into(itemView.ivContactOrGroupProfile)


        mUserModel.getMessagesOfGroup(data, onSuccess = { messageList->

            if(messageList.isNotEmpty()) {          // message history shi mha
                messageList.sortedBy { it.millis }
                itemView.tvContactOrGroupName.text = data
                if(messageList.firstOrNull()?.message != ""){
                    itemView.tvLastMessage.text = messageList.firstOrNull()?.message
                } else {
                    itemView.tvLastMessage.text = "sent a photo"
                }
            } else {
                itemView.tvLastMessage.text = "Group Created"

//                item ko phyout pho
//                val rootView = itemView.cardViewRoot
//                val params = RecyclerView.LayoutParams(0, 0)
//                itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
//
//                itemView.visibility = View.GONE
            }
        }, onFailure = {})
    }

}