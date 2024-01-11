package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.MomentPhotoPortraitAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.MessageVO
import kotlinx.android.synthetic.main.view_holder_messages.view.*


class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var mPhotoListAdapter = MomentPhotoPortraitAdapter()

    init {
        itemView.rvImageList.adapter = mPhotoListAdapter
        itemView.rvImageList.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
    }

    fun bindData(data: MessageVO, loggedInUser: String) {
//        mDataVO = data

        if (data.senderUID == loggedInUser) {
            itemView.ivFriendProfile.visibility = View.GONE
            itemView.ivFriendProfile.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))

            //
            if (data.message != "") {

                val params = itemView.llLeft.layoutParams as RelativeLayout.LayoutParams
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                params.addRule(RelativeLayout.TEXT_ALIGNMENT_GRAVITY)

                itemView.llLeft.layoutParams = params
                itemView.llLeft.gravity = Gravity.END
                itemView.llLeft.background = ContextCompat.getDrawable(itemView.context, R.drawable.background_button_accent)
                itemView.tvMessageLeft.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                itemView.tvMessageLeft.text = data.message

                itemView.tvTimeSent.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            } else {
                itemView.llLeft.visibility = View.GONE
            }


            if ( data.file?.isNotEmpty() == true) {

                val paramImage = itemView.rvImageList.layoutParams as RelativeLayout.LayoutParams
                paramImage.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)

                mPhotoListAdapter.setNewData(data.file)

            } else {
                itemView.rvImageList.visibility = View.GONE
            }
        }


        else {

            Glide.with(itemView.context)
                .load(data.senderProfile)
                .into(itemView.ivFriendProfile)

            if (data.file?.isNotEmpty() == true) {

                mPhotoListAdapter.setNewData(data.file)
            } else {
                itemView.rvImageList.visibility = View.GONE
            }


            if (data.message != "") {
                itemView.tvMessageLeft.text = data.message
            } else {
                itemView.llLeft.visibility = View.GONE
            }
        }

    }
}