package com.padcmyanmar.smtz.wechatredesign.activities

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.SignUpPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.SignUpPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SignUpView
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity(), SignUpView {

    private lateinit var mPresenter: SignUpPresenter

    private var phoneNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setUpPresenter()

        setUpOTPEditText()
        setUpListeners()

        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[SignUpPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }


    private fun setUpListeners() {
        btnBackSignUp.setOnClickListener {
            finish()
        }
        btnVerify.setOnClickListener {
            phoneNumber = etPhoneNumberSignUp.text?.trim().toString()
            val code = "${editText1.text}${editText2.text}${editText3.text}${editText4.text}"

            if(phoneNumber.trim() == ""){
                Snackbar.make(window.decorView, "Please enter phone number", Snackbar.LENGTH_LONG).show()
            } else {
                mPresenter.onTapVerify(phoneNumber, code)
            }
        }
    }

    private fun setUpOTPEditText() {

        editText1.addTextChangedListener(GenericTextWatcher(editText1, editText2))
        editText2.addTextChangedListener(GenericTextWatcher(editText2, editText3))
        editText3.addTextChangedListener(GenericTextWatcher(editText3, editText4))
        editText4.addTextChangedListener(GenericTextWatcher(editText4, null))

        editText1.setOnKeyListener(GenericKeyEvent(editText1, null))
        editText2.setOnKeyListener(GenericKeyEvent(editText2, editText1))
        editText3.setOnKeyListener(GenericKeyEvent(editText3, editText2))
        editText4.setOnKeyListener(GenericKeyEvent(editText4, editText3))
    }

    override fun navigateToSignUpProfileScreen(phone: String) {
        startActivity(SignUpProfileActivity.newIntent(this, phone))
    }

    override fun showError(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
    }

}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

// for delete
class GenericKeyEvent internal constructor(
    private val currentView: EditText,
    private val previousView: EditText?
) : View.OnKeyListener {

    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {

        if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.editText1 && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }
}

// for input, if et1 has already one char, replace it and focus et2
class GenericTextWatcher internal constructor(
    private val currentView: View,
    private val nextView: View?
) : TextWatcher {

    override fun afterTextChanged(editable: Editable) {

        val text = editable.toString()

        // replace if editText is not null
        if (editable.toString().length == 2) {
            val ab: Editable = SpannableStringBuilder(
                editable.toString().replace(editable.toString(), editable[1].toString())
            )

            editable.replace(0, editable.length, ab)
        }

        when (currentView.id) {
            R.id.editText1 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.editText2 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.editText3 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.editText4 -> if (text.length == 1) currentView.hideKeyboard()
        }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }

}