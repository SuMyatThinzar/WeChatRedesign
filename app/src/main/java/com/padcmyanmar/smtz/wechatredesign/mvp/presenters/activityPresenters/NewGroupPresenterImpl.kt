package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.components.SelectedContactsSingleton
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewGroupView

class NewGroupPresenterImpl : NewGroupPresenter, AbstractBasePresenter<NewGroupView>() {

    private var selectedContactList = mutableListOf<UserVO>()

    override fun onUiReady(owner: LifecycleOwner, currentUser: String) {
        mUserModel.getContacts(currentUser, onSuccess = { contactList->
            if (contactList.isNotEmpty()) {
                mView.showContactList(contactList)
                mView.hideEmptyView()
            } else {
                mView.showEmptyView()
            }
        }, onFailure = {
            mView.showError(it)
            mView.showEmptyView()
        })
    }

    override fun onTapCreate(groupName: String, members: List<String>) {
        mUserModel.addGroup(groupName, members)
    }

    override fun onTapContact(contact: UserVO) {

        val singletonInstance = SelectedContactsSingleton.instance
        val isSelected = singletonInstance.getAllSelectedContact().any { it.userUID == contact.userUID }

        if (isSelected) {
            singletonInstance.removeSelectedContact(contact)
        } else {
            singletonInstance.addSelectedContact(contact)
        }

        mView.showSelectedContactList(singletonInstance.getAllSelectedContact())
    }


    override fun onUiReady(owner: LifecycleOwner) {}
}