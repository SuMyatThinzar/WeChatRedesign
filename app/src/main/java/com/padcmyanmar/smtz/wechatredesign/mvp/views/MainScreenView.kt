package com.padcmyanmar.smtz.wechatredesign.mvp.views

import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface MainScreenView : BaseView {
    fun setUpUserVO(user: UserVO)
}