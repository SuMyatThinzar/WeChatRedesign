package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ChatView

class ChatPresenterImpl : ChatPresenter, AbstractBasePresenter<ChatView>() {

    private var mUserModel: UserModel = UserModelImpl
    private var mUserGroups: MutableList<GroupVO> = mutableListOf()

    override fun onUiReady(owner: LifecycleOwner, loggedInUserUID: String) {
        mUserModel.getContacts(loggedInUserUID, onSuccess = { contactList->
            mView.showContactsData(contactList)
        }, onFailure = {
            mView.showError(it)
        })

        // contact နဲ့ နောက်ဆုံးပြောခဲ့တဲ့ message
        mUserModel.getMessagedContactsOfCurrentUser(currentUser = loggedInUserUID, onSuccess = {
            mView.setUpMessages(it)
        }) {
            mView.showError(it)
        }

        // group နဲ့ နောက်ဆုံးပြောခဲ့တဲ့ message
        mUserModel.getAllGroups(onSuccess = { groupList->          // all groups user had joined
            val groupListNames : MutableSet<String> = mutableSetOf()

            groupList.forEach groupList@{ group->
                group.members!!.forEach { member->
                    if (member == loggedInUserUID) {
                        groupListNames.add(group.groupName)
//                        mUserGroups.add(group)
                        return@groupList
                    }
                }
            }
            mView.showGroupListUserJoined(groupListNames)
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