package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.viewholders.SelectedContactsViewHolder

class SelectedContactsAdapter : RecyclerView.Adapter<SelectedContactsViewHolder>() {

    private var mData: List<UserVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_selected_contacts,parent,false)
        return SelectedContactsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectedContactsViewHolder, position: Int) {
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