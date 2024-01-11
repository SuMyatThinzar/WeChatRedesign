package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.MomentActionButtonsDelegate
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MomentsView

interface MomentsPresenter : BasePresenter<MomentsView>, MomentActionButtonsDelegate {
    fun onUiReady(owner: LifecycleOwner, user: UserVO)
//    fun currentUserLikedMoments(currentUser: String): Boolean
//    fun getMomentList(currentUser: String)
}