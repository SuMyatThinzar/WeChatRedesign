package com.padcmyanmar.smtz.wechatredesign.mvp.views

import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface ContactsView : BaseView {
    fun showContactsData(contacts: List<UserVO>)
    fun hideContactListView()
    fun showContactListView()
    fun navigateToChatThread(loggedInUserUID: String, contactUID: String)
    fun showGroupListUserJoined(groups: List<String>)
    fun navigateToChatThreadFromGroup(loggedInUserUID: UserVO, group: GroupVO)
}