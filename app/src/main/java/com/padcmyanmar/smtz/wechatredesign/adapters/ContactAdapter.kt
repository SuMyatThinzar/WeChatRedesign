package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.ChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.viewholders.ContactViewHolder

class ContactAdapter(private var mDelegate: ChatThreadDelegate, private val loggedInUserUid: String) : RecyclerView.Adapter<ContactViewHolder>() {

    private var mData: List<UserVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_contacts,parent,false)
        return ContactViewHolder(itemView, mDelegate, loggedInUserUid)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        if(mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setNewData(data: List<UserVO>){
        mData = data
        notifyDataSetChanged()
    }
}