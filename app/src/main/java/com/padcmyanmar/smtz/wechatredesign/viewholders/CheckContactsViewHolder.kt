package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.SelectMemberDelegate
import kotlinx.android.synthetic.main.view_holder_check_contacts_new_group.view.*

class CheckContactsViewHolder(itemView: View, private val mDelegate: SelectMemberDelegate) : RecyclerView.ViewHolder(itemView) {

    private var mDataVO : UserVO ? = null
//    private var mMembers: MutableList<UserVO> = mutableListOf()

    init {
        itemView.setOnClickListener {
            mDataVO?.let {
                if (mDataVO?.selected == false) {
                    itemView.ivCheckContact.setImageResource(R.drawable.ic_checked)
                } else {
                    itemView.ivCheckContact.setImageDrawable(null)
                }
                mDelegate.onTapContact(it)
            }
        }
    }

    fun bindData(data: UserVO) {
        mDataVO = data

        Glide.with(itemView.context)
            .load(data.profile)
            .into(itemView.ivProfile)

        itemView.tvContactName.text = data.name

    }
}