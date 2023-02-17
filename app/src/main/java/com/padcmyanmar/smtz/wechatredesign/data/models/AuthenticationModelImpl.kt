package com.padcmyanmar.smtz.wechatredesign.data.models

import com.padcmyanmar.smtz.wechatredesign.network.AuthManager
import com.padcmyanmar.smtz.wechatredesign.network.FirebaseAuthManagerImpl

object AuthenticationModelImpl : AuthenticationModel {

    override var mAuthManager: AuthManager = FirebaseAuthManagerImpl

    override fun login(
        phone: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.login(phone, password, onSuccess, onFailure)
    }

    override fun verify(
        phone: String,
        code: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.verify(phone, code, onSuccess, onFailure)
    }

    override fun signUp(
        phone: String,
        password: String,
        userName: String,
        dateOfBirth: String,
        gender: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.signUp(phone, password, userName, dateOfBirth, gender, onSuccess, onFailure)
    }

    override fun getUserName(): String {
        return mAuthManager.getUserName()
    }


}