package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.ChatThreadDelegate
import kotlinx.android.synthetic.main.view_holder_active_people.view.*

class ActivePeopleViewHolder(itemView: View, private val mDelegate: ChatThreadDelegate, private var currentUser: String) : RecyclerView.ViewHolder(itemView) {

    private var friendVO : UserVO ? = null

    init {
        itemView.setOnClickListener {
            friendVO?.let {
                mDelegate.onTapChat(currentUser, it.userUID!!)
            }
        }
    }

    fun bindData(friend: UserVO) {
        friendVO = friend

        Glide.with(itemView.context)
            .load(friend.profile)
            .into(itemView.ivActivePersonProfileChat)

        itemView.tvActivePersonNameChat.text = friend.name
    }
}