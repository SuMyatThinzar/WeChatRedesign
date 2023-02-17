package com.padcmyanmar.smtz.wechatredesign.mvp.views

import com.padcmyanmar.smtz.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

interface ChatThreadView : BaseView {
    fun setUpCurrentUserVO(currentUserVO: UserVO)
    fun setUpContactVO(contactVO: UserVO)
    fun showMessages(messages: List<MessageVO>)
}