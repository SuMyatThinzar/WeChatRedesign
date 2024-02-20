package com.padcmyanmar.smtz.wechatredesign.components

import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO

class SelectedContactsSingleton private constructor() {

    private var selectedContactList : MutableList<UserVO> = mutableListOf()

    companion object {
        val instance : SelectedContactsSingleton by lazy {
            Holder.INSTANCE
        }
    }

    private object Holder {
        val INSTANCE = SelectedContactsSingleton()
    }

    fun cleanUp() {
        selectedContactList.clear()
    }

    fun addSelectedContact(selectedContact: UserVO) {
        selectedContactList.add(selectedContact)
    }

    fun removeSelectedContact(selectedContact: UserVO) {
        selectedContactList.remove(selectedContact)
    }

    fun getAllSelectedContact() : MutableList<UserVO> {
        return selectedContactList
    }
}