package com.padcmyanmar.smtz.wechatredesign.views.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.MomentPhotoPortraitAdapter
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.MomentActionButtonsDelegate
import kotlinx.android.synthetic.main.view_holder_moment.view.*
import java.util.concurrent.TimeUnit

class MomentViewHolder(itemView: View, private val mDelegate: MomentActionButtonsDelegate, val userUID: String) : RecyclerView.ViewHolder(itemView) {

    private var mDataVO: MomentVO? = null

    private var mMomentPhotoPortraitAdapter = MomentPhotoPortraitAdapter()

    init {
        itemView.rvMomentPhotos.adapter = mMomentPhotoPortraitAdapter
        itemView.rvMomentPhotos.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

    }

    fun bindData(data: MomentVO, mUserList: List<UserVO>) {
        mDataVO = data

        itemView.tvContent.text = data.content
        itemView.tvLikeCount.text = data.likeCount
        val time = System.currentTimeMillis() - data.millis!!
        val differentMin = TimeUnit.MILLISECONDS.toMinutes(time)

        if (differentMin < 1) {
            itemView.tvPostedTimeMoments.text = "just now"
        }
        if (differentMin in 1..59) {
            itemView.tvPostedTimeMoments.text = "$differentMin min ago"
        }
        if (differentMin > 120) {
            itemView.tvPostedTimeMoments.text = "${differentMin / 60} hour ago "
        }
        if (differentMin > 720) {
            itemView.tvPostedTimeMoments.text = "${differentMin / 720} day ago "
        }
        if (differentMin > 16500) {
            itemView.tvPostedTimeMoments.text = "${differentMin / 16500} month ago "
        }


        // bind child photo list
        data.photoList?.let {
            mMomentPhotoPortraitAdapter.setNewData(it)
        }


        if (mUserList.isNotEmpty()) {
            mUserList.forEach { user ->
                if (user.userUID == data.user) {

                    itemView.tvUserNameMoments.text = user.name
                    if (data.userProfile != "") {
                        Glide.with(itemView.context)
                            .load(user.profile)
                            .into(itemView.ivProfileMoments)
                    }
                }
            }
        }

        // listeners
        itemView.btnLike.setOnClickListener {
            val temp = mDelegate.onTapLike(data, userUID)
            setLikeButton(temp)
        }

        data.likedUsers?.let{ likedUsers->

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

        if (temp == true) {
            itemView.btnLike.setImageResource(R.drawable.ic_heart_red)
        } else {
            itemView.btnLike.setImageResource(R.drawable.ic_heart)
        }
    }
}