package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MomentsView

class MomentsPresenterImpl : MomentsPresenter, AbstractBasePresenter<MomentsView>() {


    override fun onUiReady(owner: LifecycleOwner, currentUser: UserVO) {

        // For updated user profiles
        val mUserList: ArrayList<UserVO> = arrayListOf()

        mUserModel.getAllUsers(onSuccess = { userList ->
            userList.forEach { user ->
                mUserList.add(user)
            }
            mView.setAllUserList(mUserList)
        }, onFailure = {})


        mUserModel.getMoments(onSuccess = { momentList ->

            // သက်သက် collection ထဲကမယူတော့ဘူး MomentVO ထဲမှာပဲသိမ်းထားတဲ့ likeduserlist နဲ့ပဲတိုက်စစ်တော့မလိုတော့ဘူး

//            momentList.forEach { moment ->
//                mUserModel.getMomentsLikedByUser(moment.millis!!, onSuccess = { likedUsers ->
//                    likedUsers.forEach { likedUser ->
//                        // if this user is liked the moment assign true / false
//                        moment.isLikedByUser = likedUser.userUID == currentUser.userUID
//                    }
//                }, onFailure = { mView.showError(it) })
//            }

            mView.showMomentsData(momentList.reversed())
        }, onFailure = {
            mView.showError(it)
        })
    }

    override fun onTapLike(moment: MomentVO, currentUser: String): Boolean {

        //update api if currentUser is already  liked->remove, else->add
        // this can check whether emptyList or not
        val isAlreadyLiked = moment.likedUsers?.any { it == currentUser } == true  //== true for null safety

        if (isAlreadyLiked) {
            moment.likedUsers?.remove(currentUser)
            moment.likeCount = moment.likeCount?.toInt()?.minus(1).toString()
        }
        else {
            moment.likeCount = moment.likeCount?.toInt()?.plus(1).toString()
            moment.likedUsers?.add(currentUser)
        }

        mUserModel.updateLikedUser(moment, currentUser)
        return isAlreadyLiked
    }
    override fun onTapBookmark(moment: MomentVO, currentUser: String): Boolean {

        val isAlreadyBookmarked = moment.bookmarkedUsers?.contains(currentUser) == true

        if (isAlreadyBookmarked) {
            moment.bookmarkedUsers?.remove(currentUser)
        } else {
            moment.bookmarkedUsers?.add(currentUser)
        }

        mUserModel.updateBookmarkedUser(moment)
        return isAlreadyBookmarked
    }

    override fun onTapDelete(momentId: Long) {}

    override fun onUiReady(owner: LifecycleOwner) {}

}