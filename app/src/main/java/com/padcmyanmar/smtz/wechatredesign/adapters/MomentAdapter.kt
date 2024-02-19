package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.MomentActionButtonsDelegate
import com.padcmyanmar.smtz.wechatredesign.viewholders.MomentViewHolder

class MomentAdapter(private val mDelegate: MomentActionButtonsDelegate, val userUID: String) : RecyclerView.Adapter<MomentViewHolder>() {

    private var mData: List<MomentVO> = listOf()
    private var mUserList: List<UserVO> = listOf()
    private var mType: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_moment,parent,false)
        return MomentViewHolder(itemView, mDelegate, userUID)
    }

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {

        if(mData.isNotEmpty()){

            var eachUserProfile = UserVO()

            mUserList.forEach {
                if (it.userUID == mData[position].user) {
                    eachUserProfile = it
                    return@forEach
                }
            }

            holder.bindData(mData[position], eachUserProfile, mType)
        }
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    // to prevent triggering wrong image with glide can be fixed using itemView.ivProfileMoments.setImageDrawable(null) but loading very long
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setNewData(data: List<MomentVO>, userList: List<UserVO>, type: String){
        mUserList = userList
        mData = data
        mType = type
        notifyDataSetChanged()
    }

}