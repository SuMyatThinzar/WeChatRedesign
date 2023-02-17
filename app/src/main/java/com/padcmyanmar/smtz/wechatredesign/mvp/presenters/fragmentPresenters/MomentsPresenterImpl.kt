package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MomentsView

class MomentsPresenterImpl : MomentsPresenter, AbstractBasePresenter<MomentsView>() {

    private var mUserModel: UserModel = UserModelImpl
    private var mMomentList: List<MomentVO> = listOf()
    private var mUserList: ArrayList<UserVO> = arrayListOf()

    override fun onUiReady(owner: LifecycleOwner, currentUser: UserVO) {

        mUserModel.getAllUsers(onSuccess = { userList ->
            userList.forEach { user ->
                mUserList.add(user)
            }
            mView.setUserList(mUserList)
        }, onFailure = {})


        mUserModel.getMoments(onSuccess = { momentList ->
            momentList.forEach { moment ->

                mUserModel.getMomentsLikedByUser(moment.millis!!, onSuccess = { likedUsers ->
                    likedUsers.forEach{ likedUser ->
                        if(likedUser.userUID == currentUser.userUID){

                            moment.isLikedByUser = true
                        }
                        else
                            moment.isLikedByUser = false
                    }
                }, onFailure = { mView.showError(it) })
            }
            mView.showMomentsData(momentList)
        }, onFailure = {
            mView.showError(it)
        })
    }

    override fun onTapLike(moment: MomentVO, currentUser: String): Boolean {

        val millis = System.currentTimeMillis()
        var temp = true

        //update api if currentUser is already liked->remove,else->add
        if (moment.likedUsers?.isNotEmpty() == true) {

            moment.likedUsers?.forEach{ likedUser ->

                if (likedUser == currentUser) temp = false
                else temp = true
            }

            if (!temp) mUserModel.deleteLikedUserVO(currentUser, moment)
            else mUserModel.addLikedUserVO(millis, moment, currentUser)

            return temp
        } else {
            mUserModel.addLikedUserVO(millis, moment, currentUser)
            temp = true
            return temp
        }
    }

    override fun onUiReady(owner: LifecycleOwner) {}

}