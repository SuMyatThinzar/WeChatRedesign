package com.padcmyanmar.smtz.wechatredesign.data.vos

data class MomentVO(
    var millis: Long? = null,
    var likeCount: String? = "0",
    var content: String? = null,
    var user: String? = null,
    var photoList: ArrayList<String>? = arrayListOf(),
    var likedUsers: ArrayList<String>? = arrayListOf(),      // ဒီ list ထဲကပဲ တိုက်စစ်ပြီးလုပ်တော့တယ်
    var isLikedByUser: Boolean? = false,                     // database ထဲမှာ မသိမ်းဘူး local မှာပဲ သုံးဖို့
    var bookmarkedUsers: ArrayList<String>? = arrayListOf(),
    var isBookmarkedByUser: Boolean? = false,                // database ထဲမှာ မသိမ်းဘူး local မှာပဲ သုံးဖို့
        )