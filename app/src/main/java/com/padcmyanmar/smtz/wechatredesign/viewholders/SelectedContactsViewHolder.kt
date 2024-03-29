package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import kotlinx.android.synthetic.main.view_holder_selected_contacts.view.*

class SelectedContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mDataVO : UserVO ? = null

//    init {
//        itemView.setOnClickListener {
//            mDataVO?.let {
//
//            }
//        }
//    }

    fun bindData(data: UserVO) {
        mDataVO = data

        Glide.with(itemView.context)
            .load(data.profile)
            .into(itemView.ivSelectedContactProfile)

        itemView.tvSelectedContactName.text = data.name
    }
}