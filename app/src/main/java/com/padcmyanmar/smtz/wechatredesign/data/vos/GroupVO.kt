package com.padcmyanmar.smtz.wechatredesign.data.vos

import java.io.Serializable

data class GroupVO(
     val groupName: String = "",
     val members: List<String>? = listOf(),
//     val messages: List<MessageVO>? = listOf(),  Firebase returns with key-value Pair, so it can't be converted into normal list, need to getNewPair and connect
): Serializable