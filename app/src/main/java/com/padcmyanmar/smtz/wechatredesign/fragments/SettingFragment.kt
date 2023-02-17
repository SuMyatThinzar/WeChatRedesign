package com.padcmyanmar.smtz.wechatredesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.SettingPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.SettingPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SettingView
import kotlinx.android.synthetic.main.fragment_moments.*

class SettingFragment(user: UserVO) : Fragment(), SettingView {

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

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[SettingPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {

    }

    //App Bar Leading icon
    private fun setUpToolbar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolBar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.setTitleTextAppearance(requireContext(), R.style.TextAppearance)
    }

    private fun setUpAdapters() {

    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}