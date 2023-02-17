package com.padcmyanmar.smtz.wechatredesign.data.vos

import java.io.Serializable

data class UserVO(
    var name: String? = "",
    var phone: String? = "",
    var password: String? = "",
    var dateOfBirth: String? = "",
    var gender: String? = "",
    var userUID: String? = "",
    var profile: String? = "",
    var selected: Boolean? = false,
) : Serializable