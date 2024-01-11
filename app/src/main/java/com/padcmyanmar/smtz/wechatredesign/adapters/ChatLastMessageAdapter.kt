package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.delegates.ChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.viewholders.ChatLastMessageViewHolder

class ChatLastMessageAdapter(private val mDelegate: ChatThreadDelegate, private var currentUser: String) : RecyclerView.Adapter<ChatLastMessageViewHolder>() {

    private var mContactList: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatLastMessageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat,parent,false)
        return ChatLastMessageViewHolder(itemView, mDelegate, currentUser)
    }

    override fun onBindViewHolder(holder: ChatLastMessageViewHolder, position: Int) {
        if(mContactList.isNotEmpty()){
            holder.bindData(mContactList[position])
        }
    }

    override fun getItemCount(): Int {
        return mContactList.size
    }

    fun setNewData(messagedContacts: List<String>){
        mContactList = messagedContacts
        notifyDataSetChanged()
    }
}