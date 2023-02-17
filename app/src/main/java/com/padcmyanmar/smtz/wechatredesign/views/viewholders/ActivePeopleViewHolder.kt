package com.padcmyanmar.smtz.wechatredesign.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import kotlinx.android.synthetic.main.view_holder_active_people.view.*

class ActivePeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//    private var mDataVO :  ? = null

    //    init {
//        itemView.setOnClickListener {
//            mDataVO?.let {
//
//            }
//        }
//    }
//
    fun bindData(data: UserVO) {
//        mDataVO = data

        Glide.with(itemView.context)
            .load(data.profile)
            .into(itemView.ivActivePersonProfileChat)

        itemView.tvActivePersonNameChat.text = data.name
    }
}