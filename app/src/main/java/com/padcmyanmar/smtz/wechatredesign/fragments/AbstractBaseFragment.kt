package com.padcmyanmar.smtz.wechatredesign.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.padcmyanmar.smtz.wechatredesign.mvp.views.BaseView

abstract class AbstractBaseFragment : Fragment(), BaseView {
    protected abstract fun setUpPresenter()

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}