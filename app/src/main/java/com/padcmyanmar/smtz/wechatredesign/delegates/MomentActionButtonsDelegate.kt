package com.padcmyanmar.smtz.wechatredesign.delegates

import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO

interface MomentActionButtonsDelegate {
    // to bind reaction color of logged in user and change moment's like count
    fun onTapLike(moment: MomentVO, currentUser: String) : Boolean
}