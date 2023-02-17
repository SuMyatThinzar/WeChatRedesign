package com.padcmyanmar.smtz.wechatredesign.delegates

import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface SelectMemberDelegate {
    fun onTapContact(contact: UserVO)
}