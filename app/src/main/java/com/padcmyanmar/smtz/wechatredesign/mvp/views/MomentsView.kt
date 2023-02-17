package com.padcmyanmar.smtz.wechatredesign.mvp.views

import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface MomentsView : BaseView {
    fun showMomentsData(moments: List<MomentVO>)
    fun setUserList(users: List<UserVO>)
}