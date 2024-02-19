package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MyMomentsBookmarksView
import com.padcmyanmar.smtz.wechatredesign.utils.USER_BOOKMARKED_MOMENTS
import com.padcmyanmar.smtz.wechatredesign.utils.USER_MOMENTS

class MyMomentsBookmarksPresenterImpl : MyMomentsBookmarksPresenter, AbstractBasePresenter<MyMomentsBookmarksView>() {
    override fun onUiReady(owner: LifecycleOwner, currentUser: UserVO, type: String) {

        // For updated user profiles
        val mUserList: ArrayList<UserVO> = arrayListOf()

        mUserModel.getAllUsers(onSuccess = { userList ->
            userList.forEach { user ->
                mUserList.add(user)
            }
            mView.setAllUserList(mUserList)
        }, onFailure = {})


        mUserModel.getMoments(onSuccess = { momentList ->
            var momentsToShow : List<MomentVO> = listOf()

            when (type) {       // filter specific moment list here
                USER_MOMENTS -> {
                    momentsToShow = momentList.filter {  moment ->
                        moment.user == currentUser.userUID
                    }
                }
                USER_BOOKMARKED_MOMENTS -> {
                    momentsToShow = momentList.filter { moment ->
                        moment.bookmarkedUsers?.any { bookmarkedUser ->
                            bookmarkedUser == currentUser.userUID
                        } ?: false
                    }
                }
            }

            mView.showUserMomentsData(momentsToShow.reversed())
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

    override fun onTapDelete(momentId: Long) {
        mUserModel.deleteMoment(momentId) { isSuccess, message ->
            mView.showError(message)
            if (isSuccess) {
                mView.hideLoadingView()
            }
        }
    }

    override fun onUiReady(owner: LifecycleOwner) {}
}