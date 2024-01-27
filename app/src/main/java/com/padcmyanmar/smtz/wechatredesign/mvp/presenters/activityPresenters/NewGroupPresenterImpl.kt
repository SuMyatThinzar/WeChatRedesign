package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewGroupView

class NewGroupPresenterImpl : NewGroupPresenter, AbstractBasePresenter<NewGroupView>() {

    private var mUserModel: UserModel = UserModelImpl

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
        mView.setSelectedContacts(contact)
    }


    override fun onUiReady(owner: LifecycleOwner) {}
}