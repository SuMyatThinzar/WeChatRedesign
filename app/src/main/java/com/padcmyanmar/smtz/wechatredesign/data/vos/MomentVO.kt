package com.padcmyanmar.smtz.wechatredesign.data.vos

data class MomentVO (
    var millis: Long? = null,
    var likeCount: String? = "0",
    var content: String? = null,
    var user: String? = null,
    var userName: String? = null,
    var userProfile: String? = null,
    var photoList: ArrayList<String>? = arrayListOf(),
    var likedUsers: ArrayList<String>? = arrayListOf(),
    var isLikedByUser: Boolean? = false
        )