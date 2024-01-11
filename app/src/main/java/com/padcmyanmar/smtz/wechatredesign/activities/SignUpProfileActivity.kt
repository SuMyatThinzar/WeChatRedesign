package com.padcmyanmar.smtz.wechatredesign.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.SignUpProfilePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.SignUpProfilePresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SignUpProfileView
import com.padcmyanmar.smtz.wechatredesign.utils.dayList
import com.padcmyanmar.smtz.wechatredesign.utils.yearList
import kotlinx.android.synthetic.main.activity_sign_up_profile.*

class SignUpProfileActivity : AppCompatActivity(), SignUpProfileView {

    private lateinit var mPresenter: SignUpProfilePresenter

    private lateinit var phone: String
    private var gender: String = "other"
    private lateinit var name: String
    private lateinit var password: String
    private lateinit var day: String
    private lateinit var month: String
    private lateinit var year: String

    companion object {

        const val EXTRA_PHONE_NUMBER = "EXTRA PHONE NUMBER"

        fun newIntent(context: Context, phone: String): Intent {
            val intent = Intent(context, SignUpProfileActivity::class.java)
            intent.putExtra(EXTRA_PHONE_NUMBER, phone)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_profile)
        setUpPresenter()

        setUpSpinner()
        setUpListeners()
        setUpRadioButtons()

        if (intent.getStringExtra(EXTRA_PHONE_NUMBER) != null)
            phone = intent.getStringExtra(EXTRA_PHONE_NUMBER)!!

        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[SignUpProfilePresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {

        etNameSignUp.addTextChangedListener(object : TextWatcher {
            // If you need to capture the state of the EditText before the text changes.
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // Perform real-time validation
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInputs()
            }

            // Actions that need to be performed after the text has been modified.
            override fun afterTextChanged(s: Editable?) {}
        })

        etPasswordSignUp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInputs()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        cbAgreeToTermAndService.setOnClickListener {
            validateInputs()
        }

        btnSignUpFinish.setOnClickListener {
            requestNetworkCall()
        }

        btnBackSignUp.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validateInputs() {
        name = etNameSignUp.text.toString().trim()
        password = etPasswordSignUp.text.toString().trim()
        day = spinnerDay.selectedItem.toString()
        month = spinnerMonth.selectedItem.toString()
        year = spinnerYear.selectedItem.toString()

        Log.d("adfsfadfa", "$phone, $name, $password, $day, $month, $year, $gender")

        if (!cbAgreeToTermAndService.isChecked ||
                name.isEmpty() || name == "" ||
                password.isEmpty() || password == "" ||
                day == "Day" ||
                month == "Month" ||
                year == "Year"
        ) {
            makeButtonInactive()
        }
        else {
            makeButtonActive()
        }
    }

    private fun makeButtonInactive() {
        btnSignUpFinish.background = ContextCompat.getDrawable(this, R.drawable.background_button_inactive)
        btnSignUpFinish.isEnabled = false
    }

    private fun makeButtonActive() {
        btnSignUpFinish.background = ContextCompat.getDrawable(this, R.drawable.background_button_accent)
        btnSignUpFinish.isEnabled = true
    }

    private fun requestNetworkCall() {
        mPresenter.onTapSignUp(phone = phone, name = name, password = password, day = day, month = month, year = year, gender = gender, context = this)
    }

    override fun navigateToMomentsScreen(uid: String) {
        val intent = MainScreenActivity.newIntent(this, uid)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun setUpRadioButtons() {
        rbOther.isChecked = true

        rbMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "male"
                rbFemale.isChecked = false
                rbOther.isChecked = false
            }
        }
        rbFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "female"
                rbOther.isChecked = false
                rbMale.isChecked = false
            }
        }
        rbOther.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "other"
                rbFemale.isChecked = false
                rbMale.isChecked = false
            }
        }
    }

    private fun setUpSpinner() {

//        for (day in 1..31) {
//            dayList.add(day.toString())
//        }

        val dayArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dayList)
        dayArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Day
        spinnerDay.adapter = dayArrayAdapter
        spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    val tutorialsName = parent.getItemAtPosition(position).toString()
//                    Toast.makeText(parent.context, tutorialsName, Toast.LENGTH_LONG).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // Month
        spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    val tutorialsName = parent.getItemAtPosition(position).toString()
//                    Toast.makeText(parent.context, tutorialsName, Toast.LENGTH_LONG).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Year
//        for (year in 1980..2010) {
//            yearList.add(year.toString())
//        }

        val yearArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList)
        yearArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerYear.adapter = yearArrayAdapter
        spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun showError(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
    }
}