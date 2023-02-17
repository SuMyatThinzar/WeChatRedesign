package com.padcmyanmar.smtz.wechatredesign.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.MyPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.MyPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.utils.dayList
import com.padcmyanmar.smtz.wechatredesign.utils.yearList
import kotlinx.android.synthetic.main.activity_sign_up_profile.*
import kotlinx.android.synthetic.main.dialog_user_profile.*
import kotlinx.android.synthetic.main.dialog_user_profile.view.*

class UserProfileDialogFragment : DialogFragment() {

    private var gender = ""

    companion object {
        const val TAG_UPDATE_USER = "TAG_UPDATE_USER"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_PHONE_NUMBER = "BUNDLE_PHONE_NUMBER"
        const val BUNDLE_PASSWORD = "BUNDLE_PASSWORD"
        const val BUNDLE_DATE_OF_BIRTH = "BUNDLE_DATE_OF_BIRTH"
        const val BUNDLE_GENDER = "BUNDLE_GENDER"
        const val BUNDLE_PROFILE = "BUNDLE_PROFILE"
        const val BUNDLE_UID = "BUNDLE_UID"

        fun newFragment(): UserProfileDialogFragment {
            return UserProfileDialogFragment()
        }
    }

    private lateinit var mPresenter: MyPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpRadioButtons(view)
        setUpSpinner(view)

        val uid = arguments?.getString(BUNDLE_UID)  //1
        val password = arguments?.getString(BUNDLE_PASSWORD) //2
        val profile = arguments?.getString(BUNDLE_PROFILE)   //3

        view.etNameDialog?.setText(arguments?.getString(BUNDLE_NAME))  //4
        view.etPhoneNumberDialog?.setText(arguments?.getString(BUNDLE_PHONE_NUMBER))  //5

        val dobArray = arguments?.getString(BUNDLE_DATE_OF_BIRTH)?.split("/")  //6
        view.spinnerDayDialog.setSelection(dayList.indexOf(dobArray?.get(0) ?: ""))
        view.spinnerYearDialog.setSelection(yearList.indexOf(dobArray?.get(2) ?: ""))
        view.spinnerMonthDialog.setSelection(resources.getStringArray(R.array.months).indexOf(dobArray?.get(1) ?: ""))

        when (arguments?.getString(BUNDLE_GENDER)) {  //6
            "male" -> {
                gender = "male"
                view.rbMaleDialog.isChecked = true
            }
            "female" -> {
                gender = "female"
                view.rbFemaleDialog.isChecked = true
            }
            "other" -> {
                gender = "other"
                view.rbOtherDialog.isChecked = true
            }
        }


        view.btnSave.setOnClickListener {
            val dateOfBirth = "${spinnerDayDialog.selectedItem}/${spinnerMonthDialog.selectedItem}/${spinnerYearDialog.selectedItem}"

            mPresenter.onTapSaveProfile(
                UserVO(
                    userUID = uid,
                    phone = etPhoneNumberDialog.text?.trim().toString(),
                    name = etNameDialog.text?.trim().toString(),
                    password = password,
                    dateOfBirth = dateOfBirth,
                    gender = gender,
                    profile = profile
                )
            )
            dismiss()
        }

        view.btnCancel.setOnClickListener {
            dismiss()
        }

    }

    private fun setUpPresenter() {
        activity?.let {
            mPresenter = ViewModelProviders.of(it).get(MyPresenterImpl::class.java)
        }
    }

    private fun setUpRadioButtons(view: View) {

        view.rbMaleDialog.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "male"
                view.rbFemaleDialog.isChecked = false
                view.rbOtherDialog.isChecked = false
            }
        }
        view.rbFemaleDialog.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "female"
                view.rbOtherDialog.isChecked = false
                view.rbMaleDialog.isChecked = false
            }
        }
        view.rbOtherDialog.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "other"
                view.rbFemaleDialog.isChecked = false
                view.rbMaleDialog.isChecked = false
            }

        }
    }
    private fun setUpSpinner(view: View) {

        view.spinnerDayDialog.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dayList)

        view.spinnerYearDialog.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, yearList)

    }

}