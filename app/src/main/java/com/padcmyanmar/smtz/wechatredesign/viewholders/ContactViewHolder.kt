package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.ChatThreadDelegate
import kotlinx.android.synthetic.main.view_holder_contacts.view.*

class ContactViewHolder(itemView: View, private val mDelegate: ChatThreadDelegate, private val loggedInUserUid: String) : RecyclerView.ViewHolder(itemView) {

    private var mDataVO : UserVO ? = null

    init {
        itemView.setOnClickListener {
            mDataVO?.let{ contact->
                mDelegate.onTapChat(loggedInUserUid, contact.userUID!!)
            }
        }
    }

    fun bindData(data: UserVO) {
        mDataVO = data

        Glide.with(itemView.context)
            .load(data.profile)
            .into(itemView.ivActivePersonProfile)

        itemView.tvContactName.text = data.name
    }
}