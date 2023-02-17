package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.GroupToChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.views.viewholders.GroupViewHolder

class GroupAdapter(private var mDelegate: GroupToChatThreadDelegate, private val loggedInUser: UserVO) : RecyclerView.Adapter<GroupViewHolder>() {

    private var mData: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_group,parent,false)
        return GroupViewHolder(itemView, mDelegate, loggedInUser)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        if(mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setNewData(data: List<String>){
        mData = data
        notifyDataSetChanged()
    }
}