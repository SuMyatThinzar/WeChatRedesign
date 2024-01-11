package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.graphics.Bitmap
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_holder_selected_photo.view.*

class SelectedMessagePhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mData : Bitmap ? = null

    init {
//        itemView.setOnClickListener {
//            mDataVO?.let {
//
//            }
//        }
    }

    fun bindData(data: Bitmap) {
        mData = data

//        Glide.with(itemView.context)
//            .load(mData)
//            .into(itemView.ivSelected)

        itemView.ivSelected.setImageBitmap(mData)
    }
}