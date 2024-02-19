package com.padcmyanmar.smtz.wechatredesign.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.activities.LoginActivity
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.SettingPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.SettingPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SettingView
import kotlinx.android.synthetic.main.fragment_moments.*
import kotlinx.android.synthetic.main.fragment_setting.btnLogOut

class SettingFragment(user: UserVO) : AbstractBaseFragment(), SettingView {

    private lateinit var mPresenter: SettingPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUpPresenter()
        setUpToolbar()
        setUpListeners()
        setUpAdapters()

        mPresenter.onUiReady(this)
    }

    override fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[SettingPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        btnLogOut.setOnClickListener {
            mPresenter.onTapLogout(requireContext())
        }
    }

    //App Bar Leading icon
    private fun setUpToolbar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolBar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.setTitleTextAppearance(requireContext(), R.style.TextAppearance)
    }

    private fun setUpAdapters() {

    }

    override fun navigateToLoginView() {
        // clear activity history
        val newIntent = Intent(requireContext(), LoginActivity::class.java)
        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(newIntent)
    }
}