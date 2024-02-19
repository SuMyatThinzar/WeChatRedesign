package com.padcmyanmar.smtz.wechatredesign.mvp.views

import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface MyMomentsBookmarksView : BaseView {
    fun showUserMomentsData(moments: List<MomentVO>) // will be filtered in PresenterImpl. All data is the moment list
    fun setAllUserList(users: List<UserVO>)
    fun showLoadingView()
    fun hideLoadingView()
}