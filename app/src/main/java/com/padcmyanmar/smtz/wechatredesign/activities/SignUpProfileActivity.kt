package com.padcmyanmar.smtz.wechatredesign.activities

import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_PHONE_NUMBER
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.SignUpProfilePresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.SignUpProfilePresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.SignUpProfileView
import com.padcmyanmar.smtz.wechatredesign.utils.dayList
import com.padcmyanmar.smtz.wechatredesign.utils.yearList
import kotlinx.android.synthetic.main.activity_sign_up_profile.*


class SignUpProfileActivity : AppCompatActivity(), SignUpProfileView {

    private lateinit var mPresenter: SignUpProfilePresenter

    private lateinit var phone: String
    private var gender: String = "other"

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

        cbAgreeToTermAndService.setOnClickListener {

            if (cbAgreeToTermAndService.isChecked) {
                if (
                    etNameSignUp.text.toString().trim().isNotEmpty() &&
                    spinnerDay.selectedItem.toString() != "Day" &&
                    spinnerMonth.selectedItem.toString() != "Month" &&
                    spinnerYear.selectedItem.toString() != "Year" &&
                    etPasswordSignUp.text.toString().trim().isNotEmpty()
                ) {
                    btnSignUpFinish.background =
                        ContextCompat.getDrawable(this, R.drawable.background_button_accent)

                    btnSignUpFinish.setOnClickListener {
                        mPresenter.onTapSignUp(
                            phone,
                            etNameSignUp.text.toString().trim(),
                            etPasswordSignUp.text.toString().trim(),
                            spinnerDay.selectedItem.toString(),
                            spinnerMonth.selectedItem.toString(),
                            spinnerYear.selectedItem.toString(),
                            gender
                        )
                    }
                } else {
                    btnSignUpFinish.background =
                        ContextCompat.getDrawable(this, R.drawable.background_button_inactive)
                }
            } else {
                btnSignUpFinish.background =
                    ContextCompat.getDrawable(this, R.drawable.background_button_inactive)
            }
        }

        btnBackSignUp.setOnClickListener {
//            onBackPressed()
            Snackbar.make(window.decorView, "$phone@gmail.com", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun navigateToMomentsScreen(uid: String) {
        startActivity(MainScreenActivity.newIntent(this, uid))
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