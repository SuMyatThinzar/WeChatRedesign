package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.delegates.SelectMemberDelegate
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewGroupView

interface NewGroupPresenter : BasePresenter<NewGroupView>, SelectMemberDelegate {
    fun onUiReady(owner: LifecycleOwner, currentUser: String)
    fun onTapCreate(groupName: String, members: List<String>)
}