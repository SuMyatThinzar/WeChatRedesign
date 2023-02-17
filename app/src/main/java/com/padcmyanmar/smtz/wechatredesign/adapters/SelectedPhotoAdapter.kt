package com.padcmyanmar.smtz.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.views.viewholders.SelectedPhotoViewHolder

class SelectedPhotoAdapter : RecyclerView.Adapter<SelectedPhotoViewHolder>() {

    private var mData: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedPhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_selected_photo,parent,false)
        return SelectedPhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectedPhotoViewHolder, position: Int) {
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