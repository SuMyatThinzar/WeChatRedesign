package com.padcmyanmar.smtz.wechatredesign.activities

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.mvp.views.BaseView

abstract class AbstractBaseActivity : BaseView, AppCompatActivity()  {

    protected abstract fun setUpPresenter()

    override fun showError(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
    }
}