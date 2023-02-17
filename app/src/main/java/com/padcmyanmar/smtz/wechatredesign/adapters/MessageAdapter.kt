package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.smtz.wechatredesign.views.viewholders.MessagesViewHolder

class MessageAdapter(private val loggedInUser: String) : RecyclerView.Adapter<MessagesViewHolder>() {

    private var mData: List<MessageVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_messages,parent,false)
        return MessagesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.setIsRecyclable(false)  // not to mess up views when binding new data

        if(mData.isNotEmpty()){
            holder.bindData(mData[position], loggedInUser)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setNewData(data: List<MessageVO>){
        mData = data
        notifyDataSetChanged()
    }
}