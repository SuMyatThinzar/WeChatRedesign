package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.ChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.viewholders.ActivePeopleViewHolder

class ActivePeopleAdapter(private val mDelegate: ChatThreadDelegate, private var currentUser: String) : RecyclerView.Adapter<ActivePeopleViewHolder>() {

    private var mData: List<UserVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivePeopleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_active_people,parent,false)
        return ActivePeopleViewHolder(itemView, mDelegate, currentUser)
    }

    override fun onBindViewHolder(holder: ActivePeopleViewHolder, position: Int) {
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