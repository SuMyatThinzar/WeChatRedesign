package com.padcmyanmar.smtz.wechatredesign.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.SplashPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.SplashPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SplashScreenView
import com.padcmyanmar.smtz.wechatredesign.utils.customPrefs
import com.padcmyanmar.smtz.wechatredesign.utils.get
import kotlinx.android.synthetic.main.activity_splash.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AbstractBaseActivity(), SplashScreenView {

    private lateinit var mPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btnLoginSplash.visibility = View.INVISIBLE
        btnSignUpSplash.visibility = View.INVISIBLE

        Handler().postDelayed(Runnable{
            val preference = customPrefs(applicationContext, "user_login")
            val uid = preference.get("uid", "")

            if(uid.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                startActivity(MainScreenActivity.newIntent(this, uid))
                finish()
            } else {
                progressBar.visibility = View.INVISIBLE
                btnLoginSplash.visibility = View.VISIBLE
                btnSignUpSplash.visibility = View.VISIBLE
            }
        }, 1500L)

        setUpPresenter()

        setUpListeners()

        mPresenter.onUiReady(this)

    }

    override fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[SplashPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnLoginSplash.setOnClickListener{
            mPresenter.onTapLogin()
        }
        btnSignUpSplash.setOnClickListener{
            mPresenter.onTapSignUp()
        }
    }

    override fun navigateToSignUpScreen() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun navigateToLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}