package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.views.viewholders.MomentPhotoPortraitViewHolder

class MomentPhotoPortraitAdapter : RecyclerView.Adapter<MomentPhotoPortraitViewHolder>() {

    private var mData: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentPhotoPortraitViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_moment_photo_portrait,parent,false)
        return MomentPhotoPortraitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MomentPhotoPortraitViewHolder, position: Int) {
        if(mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    fun setNewData(data: List<String>){
        mData = data
        notifyDataSetChanged()
    }
}