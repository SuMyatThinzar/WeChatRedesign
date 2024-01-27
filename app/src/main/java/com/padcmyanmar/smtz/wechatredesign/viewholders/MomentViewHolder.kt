package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.MomentPhotoPortraitAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.MomentActionButtonsDelegate
import com.padcmyanmar.smtz.wechatredesign.utils.timestampToDateString
import com.padcmyanmar.smtz.wechatredesign.utils.timestampToTimeString
import kotlinx.android.synthetic.main.view_holder_moment.view.*
import java.util.concurrent.TimeUnit

class MomentViewHolder(itemView: View, private val mDelegate: MomentActionButtonsDelegate, val userUID: String) : RecyclerView.ViewHolder(itemView) {

    private var mDataVO: MomentVO? = null

    private var mMomentPhotoPortraitAdapter = MomentPhotoPortraitAdapter()

    init {
        itemView.rvMomentPhotos.adapter = mMomentPhotoPortraitAdapter
        itemView.rvMomentPhotos.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun bindData(data: MomentVO, momentUser: UserVO) {
        mDataVO = data

        itemView.tvLikeCount.text = data.likeCount
        itemView.tvUserNameMoments.text = momentUser.name

        if (data.content?.isNotEmpty() == true) {
            itemView.tvContent.visibility = View.VISIBLE
            itemView.tvContent.text = data.content
        } else {
            itemView.tvContent.visibility = View.GONE
        }

        if (momentUser.profile != "") {
            Glide.with(itemView.context)
                .load(momentUser.profile)
                .into(itemView.ivProfileMoments)
        }

        val differenceMillis = System.currentTimeMillis() - data.millis!!  // differences between current time and posted time
        val differentMin = TimeUnit.MILLISECONDS.toMinutes(differenceMillis)
        val differentHour = TimeUnit.MILLISECONDS.toHours(differenceMillis)
        val differentDay = TimeUnit.MILLISECONDS.toDays(differenceMillis)
        val differentMonth = differentDay / 30  // assuming a 30-day month
        val differentYear = differentDay / 365  // approximate number of days in a year

        var textToShow = ""

        when {
            differentMin < 1             -> textToShow = "just now"
            differentMin < 60            -> textToShow = "$differentMin minute ago"
            differentHour in 1..24  -> textToShow = "$differentHour hour ago"
            differenceMillis > 86400000L  -> textToShow =
              "${timestampToDateString(data.millis ?: 0)} - ${timestampToTimeString(data.millis ?: 0)}"  // if more than 24 hours/a day
        }

        // condition check with minute,hour,day,month,year
//        when {
//            differentMin < 1         -> textToShow = "just now"
//            differentMin < 60        -> textToShow = "$differentMin minute ago"
//            differentHour in 1..24   -> textToShow = "$differentHour hour ago"
//            differentDay in 1..30    -> textToShow = "$differentDay day ago"
//            differentMonth in 1..11  -> textToShow = "$differentMonth month ago"
//            differentYear > 0        -> textToShow = "$differentYear year ago"
//        }

        itemView.tvPostedTimeMoments.text = textToShow

        // condition check with Millis
//        when {
//            differentMin < 1 -> {
//                itemView.tvPostedTimeMoments.text = "just now"
//            }
//            differentMin in 1..59 -> {    // less than 1 hour
//                itemView.tvPostedTimeMoments.text = "$differentMin min ago"
//            }
//            differentMin in 60..1439 -> {   // over an hour but less than 24 hour
//                itemView.tvPostedTimeMoments.text = "${differentMin / 60} hour ago"
//            }
//            differentMin in 1440..43199 -> {   // over 1 day (1440 minutes) but less than 30 day
//                itemView.tvPostedTimeMoments.text = "${differentMin / 1440} day ago"
//            }
//            differentMin > 43200 -> {   // over 1 month
//                itemView.tvPostedTimeMoments.text = "${differentMin / 43200} month ago"
//            }
//        }

        // bind child photo list
        if (data.photoList?.isNotEmpty() == true) {
            mMomentPhotoPortraitAdapter.setNewData(data.photoList!!)
        } else {
            itemView.rvMomentPhotos.visibility = View.GONE
        }

        // listeners
        itemView.btnLike.setOnClickListener {
            val temp = mDelegate.onTapLike(data, userUID)
            setLikeButton(temp)
        }

        data.likedUsers?.let { likedUsers->

            for (liker in likedUsers) {
                if (liker == userUID) {
                    data.isLikedByUser = true
                    break
                } else {
                    data.isLikedByUser = false
                }
            }
            data.isLikedByUser?.let { setLikeButton(it) }
        }
    }

    private fun setLikeButton(temp: Boolean) {

        if (temp) {
            itemView.btnLike.setImageResource(R.drawable.ic_heart_red)
        } else {
            itemView.btnLike.setImageResource(R.drawable.ic_heart)
        }
    }
}