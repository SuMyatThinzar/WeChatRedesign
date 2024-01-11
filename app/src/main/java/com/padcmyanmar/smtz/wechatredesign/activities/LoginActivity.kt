package com.padcmyanmar.smtz.wechatredesign.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.LoginPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.LoginPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var mPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUpPresenter()

        setUpListeners()

        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[LoginPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnLogin.setOnClickListener{
            val phone = etPhoneNumberLogin.text?.trim().toString()
            val password = etPasswordLogin.text?.trim().toString()

            if(phone == "" || password == ""){
                Snackbar.make(window.decorView, "Please enter phone number and password", Snackbar.LENGTH_LONG).show()
                error("Please enter phone number and password")
            } else {
                mPresenter.onTapLogin(phone, password, applicationContext)
            }
        }

        btnBackLogin.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun navigateToMomentView(uid: String) {
        startActivity(MainScreenActivity.newIntent(this, uid))
    }

    override fun showError(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
    }
}