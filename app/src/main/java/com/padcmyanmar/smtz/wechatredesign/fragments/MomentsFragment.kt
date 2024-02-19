package com.padcmyanmar.smtz.wechatredesign.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.activities.NewMomentActivity
import com.padcmyanmar.smtz.wechatredesign.adapters.MomentAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.MomentsPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.MomentsPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MomentsView
import com.padcmyanmar.smtz.wechatredesign.utils.ALL_MOMENTS
import kotlinx.android.synthetic.main.fragment_moments.*

class MomentsFragment(private val user: UserVO) : AbstractBaseFragment(), MomentsView {

    private lateinit var mPresenter: MomentsPresenter

    private lateinit var mMomentAdapter: MomentAdapter
    private var mUserList: List<UserVO> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUpPresenter()
        setUpToolbar()
        setUpListeners()
        setUpAdapters()

        mPresenter.onUiReady(this, user)
    }

    override fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MomentsPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        btnNewMoment.setOnClickListener {
            startActivity(NewMomentActivity.newIntent(requireContext(), user))
        }
    }

    //App Bar Leading icon
    private fun setUpToolbar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolBar)
        toolBar.setTitleTextAppearance(requireContext(), R.style.TextAppearance)
    }

    private fun setUpAdapters() {
        mMomentAdapter = MomentAdapter(mPresenter, user.userUID!!)
        rvMoment.adapter = mMomentAdapter
        rvMoment.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun setAllUserList(users: List<UserVO>) {
        mUserList = users
    }

    override fun showMomentsData(moments: List<MomentVO>) {
        mMomentAdapter.setNewData(moments, mUserList, ALL_MOMENTS)
    }
}