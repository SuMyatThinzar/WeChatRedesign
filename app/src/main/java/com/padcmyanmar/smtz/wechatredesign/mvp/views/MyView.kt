package com.padcmyanmar.smtz.wechatredesign.mvp.views

import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface MyView : BaseView {
    fun showDialog(user: UserVO)
//    fun navigateToLoginView()
//    fun setUpUserVO(user: UserVO)
}