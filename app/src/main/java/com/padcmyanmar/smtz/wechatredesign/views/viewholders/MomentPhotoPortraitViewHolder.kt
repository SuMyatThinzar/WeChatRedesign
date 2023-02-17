package com.padcmyanmar.smtz.wechatredesign.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.services.DownloadImageWorker
import com.padcmyanmar.smtz.wechatredesign.services.DownloadImageWorker.Companion.DOWNLOAD_IMAGE
import kotlinx.android.synthetic.main.view_holder_moment_photo_portrait.view.*
import kotlinx.coroutines.withContext

class MomentPhotoPortraitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mData : String ? = null

    init {
        itemView.setOnLongClickListener {
            mData?.let {

                //1.
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .setRequiresStorageNotLow(true)
                    .build()

                val imageUrl = Data.Builder().putString(DOWNLOAD_IMAGE, mData)  //custom Url

                //2.
//                val downloadWorkResult = OneTimeWorkRequest.Builder(DownloadImageWorker::class.java)
                val downloadWorkRequest = OneTimeWorkRequestBuilder<DownloadImageWorker>()
                    .setInputData(imageUrl.build())
                    .setConstraints(constraints)
                    .build()

                //3.
                WorkManager.getInstance(itemView.context)
                    .enqueue(downloadWorkRequest)
            }
            true
        }
    }

    fun bindData(data: String) {
        mData = data

        Glide.with(itemView.context)
            .load(mData)
            .into(itemView.ivMomentPhoto)

    }
}