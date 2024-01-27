package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.MomentPhotoPortraitAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.smtz.wechatredesign.utils.timestampToTimeString
import kotlinx.android.synthetic.main.view_holder_messages.view.*


class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mPhotoListAdapter = MomentPhotoPortraitAdapter()

    init {
        itemView.rvImageList.adapter = mPhotoListAdapter
        itemView.rvImageList.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
    }

    fun bindData(data: MessageVO, loggedInUser: String) {

        if (data.message != "") {
            itemView.tvMessageLeft.text = data.message
            itemView.tvTimeSent.text = timestampToTimeString(data.millis ?: 0)
        } else {
            itemView.llLeft.visibility = View.GONE
        }

        if (data.file?.isNotEmpty() == true) {
            itemView.rvImageList.visibility = View.VISIBLE
            mPhotoListAdapter.setNewData(data.file)
        } else {
            itemView.rvImageList.visibility = View.GONE
        }


        // if the message owner is loggedInUser
        if (data.senderUID == loggedInUser) {

            if (data.message != "") {

                // send the layout to Right and change background and text colors
                val params = itemView.llLeft.layoutParams as RelativeLayout.LayoutParams
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                params.addRule(RelativeLayout.TEXT_ALIGNMENT_GRAVITY)

                itemView.llLeft.layoutParams = params
                itemView.llLeft.gravity = Gravity.END
                itemView.llLeft.background = ContextCompat.getDrawable(itemView.context, R.drawable.background_button_accent)

                itemView.tvMessageLeft.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                itemView.tvTimeSent.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            }

            if (data.file?.isNotEmpty() == true) {
                itemView.rvImageList.visibility = View.VISIBLE
                val paramImage = itemView.rvImageList.layoutParams as RelativeLayout.LayoutParams
                paramImage.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            }
        }

        // if the message owner is not a loggedInUser
        else {
            Glide.with(itemView.context)
                .load(data.senderProfile)
                .into(itemView.ivFriendProfile)
        }
    }
}