package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.MomentActionButtonsDelegate
import com.padcmyanmar.smtz.wechatredesign.views.viewholders.MomentViewHolder

class MomentAdapter(private val mDelegate: MomentActionButtonsDelegate, val userUID: String) : RecyclerView.Adapter<MomentViewHolder>() {

    private var mData: List<MomentVO> = listOf()
    private var mUserList: List<UserVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_moment,parent,false)
        return MomentViewHolder(itemView, mDelegate, userUID)
    }

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
        if(mData.isNotEmpty()){
            holder.bindData(mData[position], mUserList)
        }
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    fun setNewData(data: List<MomentVO>, userList: List<UserVO>){
        mUserList = userList
        mData = data.reversed()
        notifyDataSetChanged()
    }
}