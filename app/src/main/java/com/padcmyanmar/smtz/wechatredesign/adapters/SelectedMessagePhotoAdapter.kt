package com.padcmyanmar.smtz.wechatredesign.adapters

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.views.viewholders.SelectedMessagePhotoViewHolder
import com.padcmyanmar.smtz.wechatredesign.views.viewholders.SelectedPhotoViewHolder

class SelectedMessagePhotoAdapter : RecyclerView.Adapter<SelectedMessagePhotoViewHolder>() {

    private var mData: ArrayList<Bitmap> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedMessagePhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_selected_photo,parent,false)
        return SelectedMessagePhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectedMessagePhotoViewHolder, position: Int) {
        if(mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setNewData(data: ArrayList<Bitmap>){
        mData = data
        notifyDataSetChanged()
    }
}