package com.padcmyanmar.smtz.wechatredesign.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.MomentAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.MyMomentsBookmarksPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.MyMomentsBookmarksPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MyMomentsBookmarksView
import kotlinx.android.synthetic.main.activity_my_moments_bookmarks.btnBack
import kotlinx.android.synthetic.main.activity_my_moments_bookmarks.loadingView
import kotlinx.android.synthetic.main.activity_my_moments_bookmarks.rvMoment
import kotlinx.android.synthetic.main.activity_my_moments_bookmarks.toolBar

class MyMomentsBookmarksActivity : AbstractBaseActivity(), MyMomentsBookmarksView {

    private lateinit var mPresenter: MyMomentsBookmarksPresenter

    private var mUserVO : UserVO = UserVO()
    private var type: String = ""
    private var mUserList: List<UserVO> = listOf()
    private var mUserMomentList: List<MomentVO> = listOf()
    private var mUserBookmarks: List<MomentVO> = listOf()
    private lateinit var mMomentAdapter: MomentAdapter

    companion object {
        private const val USER_VO = "USER VO"
        private const val EXTRA_TYPE = "EXTRA TYPE"

        fun newIntent(context: Context, userVO: UserVO, type: String) : Intent {
            val intent = Intent(context, MyMomentsBookmarksActivity::class.java)
            intent.putExtra(USER_VO, userVO)
            intent.putExtra(EXTRA_TYPE, type)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_moments_bookmarks)

        setUpPresenter()

        mUserVO = intent.getSerializableExtra(USER_VO) as UserVO
        type = intent.getStringExtra(EXTRA_TYPE).toString()

        setUpToolbar()
        setUpListeners()
        setUpAdapters()

        mPresenter.onUiReady(this, mUserVO, type)
    }

    override fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MyMomentsBookmarksPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolBar)
        toolBar.title = type
        toolBar.setTitleTextAppearance(this, R.style.TextAppearanceCreateMoment)
    }

    private fun setUpListeners() {
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setUpAdapters() {
        mMomentAdapter = MomentAdapter(mPresenter, mUserVO.userUID!!)
        rvMoment.adapter = mMomentAdapter
        rvMoment.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

    override fun setAllUserList(users: List<UserVO>) {
        mUserList = users
    }

    // will be filtered in PresenterImpl. All data is the moment list
    override fun showUserMomentsData(moments: List<MomentVO>) {
        mMomentAdapter.setNewData(moments, mUserList, type)
    }

    override fun showLoadingView() {
        loadingView.visibility = View.VISIBLE
        rvMoment.isClickable = false
        rvMoment.isFocusable = false
    }

    override fun hideLoadingView() {
        loadingView.visibility = View.GONE
        rvMoment.isClickable = true
        rvMoment.isFocusable = true
    }

}