package com.padcmyanmar.smtz.wechatredesign.mvp.views

import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface ChatView : BaseView {
    fun showContactsData(contacts: List<UserVO>)
    fun showGroupListUserJoined(groupsUserJoined: MutableSet<String>)
    fun setUpMessages(messagedContacts: List<String>)
    fun navigateToChatThread(loggedInUserUID: String, contactUID: String)
    fun navigateToChatThreadFromGroup(loggedInUser: UserVO, group: GroupVO)
}