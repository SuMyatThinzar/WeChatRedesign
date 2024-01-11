package com.padcmyanmar.smtz.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.GroupToChatThreadDelegate
import kotlinx.android.synthetic.main.view_holder_group.view.*

class GroupViewHolder(itemView: View, private var mDelegate: GroupToChatThreadDelegate, private val loggedInUser: UserVO) : RecyclerView.ViewHolder(itemView) {

    private var mDataVO : GroupVO ? = null
    private var mUserModel: UserModel = UserModelImpl

    init {
        itemView.setOnClickListener {
            mDataVO?.let {
                mDelegate.onTapGroup(loggedInUser, it)
            }
        }
    }

    fun bindData(data: String) {

        mUserModel.getGroup(data, onSuccess = {
            mDataVO = it
        }, onFailure = {})

//        Glide.with(itemView.context)
//            .load("${IMAGE_BASE_URL}${movie.posterPath}")
//            .into(itemView.ivShowcase)

        itemView.tvGroupName.text = data
    }
}