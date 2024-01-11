package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.GroupToChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.viewholders.GroupLastMessageViewHolder

class GroupLastMessageAdapter(private val mDelegate: GroupToChatThreadDelegate, private var currentUser: UserVO) : RecyclerView.Adapter<GroupLastMessageViewHolder>() {

    private var mData: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupLastMessageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat,parent,false)
        return GroupLastMessageViewHolder(itemView, mDelegate, currentUser)
    }

    override fun onBindViewHolder(holder: GroupLastMessageViewHolder, position: Int) {
        if(mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    fun setNewData(data: MutableSet<String>){
        mData = data.toList()
        notifyDataSetChanged()
    }

}