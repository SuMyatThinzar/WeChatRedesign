package com.padcmyanmar.smtz.wechatredesign.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.delegates.SelectMemberDelegate
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewGroupView

interface NewGroupPresenter : BasePresenter<NewGroupView>, SelectMemberDelegate {
    fun onUiReady(owner: LifecycleOwner, currentUser: String)
    fun onTapCreate(groupName: String, members: List<String>)
}