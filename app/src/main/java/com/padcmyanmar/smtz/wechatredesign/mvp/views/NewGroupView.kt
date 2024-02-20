package com.padcmyanmar.smtz.wechatredesign.mvp.views

import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface NewGroupView : BaseView{
    fun showContactList(contacts: List<UserVO>)
    fun showSelectedContactList(contacts: List<UserVO>)
    fun showEmptyView()
    fun hideEmptyView()
}