package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ContactsView

class ContactsPresenterImpl : ContactsPresenter, AbstractBasePresenter<ContactsView>() {

    private var mUserModel: UserModel = UserModelImpl
    private var mUserGroups: MutableList<GroupVO> = mutableListOf()

    override fun onUiReady(owner: LifecycleOwner, loggedInUserUID: String) {
        //contacts
        mUserModel.getContacts(loggedInUserUID, onSuccess = { contactList->
            mView.showContactsData(contactList)
        }, onFailure = {
            mView.showError(it)
        })

        //groups
        mUserModel.getAllGroups(onSuccess = { groupList->
            val groupListNames : MutableSet<String> = mutableSetOf()

            groupList.forEach groupList@{ group->
                group.members!!.forEach { member->
                    if (member == loggedInUserUID) {
                        groupListNames.add(group.groupName)
//                        mUserGroups.add(group)             Set nae ma lok yin Msg poh tine mUserGroups ko group add twr dl
                        return@groupList
                    }
                }
            }
            mView.showGroupListUserJoined(groupListNames.toList())
        }, onFailure = {
            mView.showError(it)
        })
    }

    override fun onTapAddContact(loggedInUser: UserVO, contactUID: String) {

        mUserModel.getUser( contactUID,
            onSuccess = { contact ->
                mUserModel.addContactsEachOther(loggedInUser, contact)
        }, onFailure = {
            mView.showError(it)
        })
    }

    override fun onTapChat(loggedInUserUID: String, contactUID: String) {
        mView.navigateToChatThread(loggedInUserUID, contactUID)
    }

    override fun onTapGroup(loggedInUser: UserVO, group: GroupVO) {
        mView.navigateToChatThreadFromGroup(loggedInUser, group)
    }

    override fun onUiReady(owner: LifecycleOwner) {}

}