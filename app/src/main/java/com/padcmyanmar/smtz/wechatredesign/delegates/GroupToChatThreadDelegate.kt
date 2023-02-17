package com.padcmyanmar.smtz.wechatredesign.delegates

import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface GroupToChatThreadDelegate {
    fun onTapGroup(loggedInUser: UserVO, group: GroupVO)
}