package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.MomentPhotoPortraitAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.MomentActionButtonsDelegate
import com.padcmyanmar.smtz.wechatredesign.utils.ALL_MOMENTS
import com.padcmyanmar.smtz.wechatredesign.utils.USER_BOOKMARKED_MOMENTS
import com.padcmyanmar.smtz.wechatredesign.utils.USER_MOMENTS
import com.padcmyanmar.smtz.wechatredesign.utils.timestampToDateString
import com.padcmyanmar.smtz.wechatredesign.utils.timestampToTimeString
import kotlinx.android.synthetic.main.view_holder_moment.view.*
import java.util.concurrent.TimeUnit

class MomentViewHolder(itemView: View, private val mDelegate: MomentActionButtonsDelegate, val userUID: String) : RecyclerView.ViewHolder(itemView) {

    private var momentVO: MomentVO? = null

    private var mMomentPhotoPortraitAdapter = MomentPhotoPortraitAdapter()

    init {
        itemView.rvMomentPhotos.adapter = mMomentPhotoPortraitAdapter
        itemView.rvMomentPhotos.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

        // listeners
        itemView.btnLike.setOnClickListener {
            momentVO?.let { momentVO ->
                val isLiked = mDelegate.onTapLike(momentVO, userUID)
                setLikeButton(isLiked)
            }
        }

        itemView.btnBookmark.setOnClickListener {
            momentVO?.let { momentVO ->
                val isBookmarked = mDelegate.onTapBookmark(momentVO, userUID)
                setBookmarkButton(isBookmarked)
            }
        }

        itemView.moreSetting.setOnClickListener {
            momentVO?.let { momentVO ->
                val popupMenu = PopupMenu(itemView.context, it)
                popupMenu.inflate(R.menu.option_menu)

                popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                    when (menuItem.itemId) {
                        R.id.action_delete -> {
                            mDelegate.onTapDelete(momentVO.millis!!)
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }
        }
    }

    fun bindData(data: MomentVO, momentUser: UserVO, type: String) {
        momentVO = data

        itemView.tvLikeCount.text = data.likeCount
        itemView.tvUserNameMoments.text = momentUser.name

        // Text
        if (data.content?.isNotEmpty() == true) {
            itemView.tvContent.visibility = View.VISIBLE
            itemView.tvContent.text = data.content
        } else {
            itemView.tvContent.visibility = View.GONE
        }

//        itemView.ivProfileMoments.setImageDrawable(null)
        if (momentUser.profile != "") {
            Glide.with(itemView.context)
                .load(momentUser.profile)
                .into(itemView.ivProfileMoments)
        }

        val differenceMillis = System.currentTimeMillis() - data.millis!!  // differences between current time and posted time
        val differentMin = TimeUnit.MILLISECONDS.toMinutes(differenceMillis)
        val differentHour = TimeUnit.MILLISECONDS.toHours(differenceMillis)

        var textToShow = ""

        when {
            differentMin < 1             -> textToShow = "just now"
            differentMin < 60            -> textToShow = "$differentMin minute ago"
            differentHour in 1..24  -> textToShow = "$differentHour hour ago"
            differenceMillis > 86400000L  -> textToShow =
              "${timestampToDateString(data.millis ?: 0)} - ${timestampToTimeString(data.millis ?: 0)}"  // if more than 24 hours/a day
        }

        itemView.tvPostedTimeMoments.text = textToShow

        // bind child photo list
        if (data.photoList?.isNotEmpty() == true) {
            mMomentPhotoPortraitAdapter.setNewData(data.photoList!!)
            itemView.rvMomentPhotos.visibility = View.VISIBLE
        } else {
            itemView.rvMomentPhotos.visibility = View.GONE
        }

        // current user က like လုပ်ထားမထား
        data.isLikedByUser = data.likedUsers?.any { it == userUID }  // no need to loop, no need condition check
        data.isLikedByUser?.let { setLikeButton(it) }

        // current user က bookmark လုပ်ထားမထား
        data.isBookmarkedByUser = data.bookmarkedUsers?.any { it == userUID }
        data.isBookmarkedByUser?.let { setBookmarkButton(it) }


        // more button visibility
        itemView.moreSetting.apply {
            when (type) {
                ALL_MOMENTS -> visibility = View.GONE
                USER_MOMENTS -> visibility = View.VISIBLE
                USER_BOOKMARKED_MOMENTS -> visibility = View.GONE
            }
        }
    }

    private fun setLikeButton(temp: Boolean) {

        if (temp) {
            itemView.btnLike.setImageResource(R.drawable.ic_heart_red)
        } else {
            itemView.btnLike.setImageResource(R.drawable.ic_heart)
        }
    }

    private fun setBookmarkButton(temp: Boolean) {

        if (temp) {
            itemView.btnBookmark.setImageResource(R.drawable.ic_bookmark_red)
        } else {
            itemView.btnBookmark.setImageResource(R.drawable.ic_bookmark)
        }
    }
}