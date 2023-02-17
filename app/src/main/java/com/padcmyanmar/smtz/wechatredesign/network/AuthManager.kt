package com.padcmyanmar.smtz.wechatredesign.network

interface AuthManager {
    fun login(phone: String, password: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit)
    fun signUp(phone: String, password: String, userName: String, dateOfBirth: String, gender: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit)
    fun verify(phone: String, code: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun getUserName() : String
}