package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.BasePresenter
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.delegates.ChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.delegates.GroupToChatThreadDelegate
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ContactsView

interface ContactsPresenter : BasePresenter<ContactsView>, ChatThreadDelegate, GroupToChatThreadDelegate {
    fun onUiReady(owner: LifecycleOwner, loggedInUserUID: String)
    fun onTapAddContact(loggedInUser: UserVO, contactUID: String)
}