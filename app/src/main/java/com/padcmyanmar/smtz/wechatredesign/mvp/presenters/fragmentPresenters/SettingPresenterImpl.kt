package com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SettingView
import com.padcmyanmar.smtz.wechatredesign.utils.customPrefs
import com.padcmyanmar.smtz.wechatredesign.utils.set

class SettingPresenterImpl : SettingPresenter, AbstractBasePresenter<SettingView>() {

    override fun onUiReady(owner: LifecycleOwner) {

    }

    override fun onTapLogout(context: Context) {
        val preference = customPrefs(context, "user_login")
        preference.set("uid", "")
        mView.navigateToLoginView()
    }
}