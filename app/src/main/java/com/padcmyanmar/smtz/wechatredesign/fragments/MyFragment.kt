package com.padcmyanmar.smtz.wechatredesign.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.activities.LoginActivity
import com.padcmyanmar.smtz.wechatredesign.activities.MainScreenActivity
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.MyPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.MyPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MyView
import com.padcmyanmar.smtz.wechatredesign.utils.customPrefs
import com.padcmyanmar.smtz.wechatredesign.utils.set
import com.padcmyanmar.smtz.wechatredesign.dialogs.QRCodeDialogFragment
import com.padcmyanmar.smtz.wechatredesign.dialogs.UserProfileDialogFragment
import com.padcmyanmar.smtz.wechatredesign.dialogs.UserProfileDialogFragment.Companion.BUNDLE_DATE_OF_BIRTH
import com.padcmyanmar.smtz.wechatredesign.dialogs.UserProfileDialogFragment.Companion.BUNDLE_GENDER
import com.padcmyanmar.smtz.wechatredesign.dialogs.UserProfileDialogFragment.Companion.BUNDLE_NAME
import com.padcmyanmar.smtz.wechatredesign.dialogs.UserProfileDialogFragment.Companion.BUNDLE_PASSWORD
import com.padcmyanmar.smtz.wechatredesign.dialogs.UserProfileDialogFragment.Companion.BUNDLE_PHONE_NUMBER
import com.padcmyanmar.smtz.wechatredesign.dialogs.UserProfileDialogFragment.Companion.BUNDLE_PROFILE
import com.padcmyanmar.smtz.wechatredesign.dialogs.UserProfileDialogFragment.Companion.BUNDLE_UID
import kotlinx.android.synthetic.main.fragment_moments.toolBar
import kotlinx.android.synthetic.main.fragment_my.*
import java.io.IOException


class MyFragment(private val mUser: UserVO) : Fragment(), MyView {

    private lateinit var mPresenter: MyPresenter

//    lateinit var mUser: UserVO
    private var mUserProfileDialogFragment: UserProfileDialogFragment? = null
    private var mQRCodeDialogFragment: QRCodeDialogFragment? = null
    private var mQrImageBitmap: Bitmap? = null

    companion object{
        const val PICK_IMAGE_REQUEST = 1111
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUpPresenter()
        setUpToolbar()
        setUpAdapters()
        bindMyData()
        setUpListeners()

        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MyPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        btnEditProfile.setOnClickListener {
            mPresenter.onTapEditProfile(mUser)
        }

        btnLogOut.setOnClickListener {
            val preference = customPrefs(requireContext(), "user_login")
            preference.set("uid", "")
            mPresenter.onTapLogout()
        }

        ivGallery.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        ivMyQrCode.setOnClickListener {

            mQrImageBitmap?.let{
                mQRCodeDialogFragment = QRCodeDialogFragment(it)
                mQRCodeDialogFragment?.show(
                    parentFragmentManager,
                    QRCodeDialogFragment.TAG_SHOW_BIG_IMAGE
                )
            }
        }
    }

    //App Bar Leading icon
    private fun setUpToolbar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolBar)
        toolBar.setTitleTextAppearance(requireContext(), R.style.TextAppearance)
    }

    private fun setUpAdapters() {
//        mMomentAdapter = MomentAdapter()
//        rvMoment.adapter = mMomentAdapter
//        rvMoment.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun bindMyData(){
        tvMyUserName.text = mUser.name
        tvMyPhoneNumber.text = mUser.phone
        tvMyDateOfBirth.text = mUser.dateOfBirth?.replace('/', '-')
        tvMyGender.text = mUser.gender

        if(mUser.profile != ""){
            Glide.with(this)
                .load(mUser.profile)
                .into(ivMyProfile)
        }


        // String to QR code encoder
        val barcodeEncoder = BarcodeEncoder()
        mQrImageBitmap = barcodeEncoder.encodeBitmap(mUser.userUID, BarcodeFormat.QR_CODE, 400, 400)
        ivMyQrCode.setImageBitmap(mQrImageBitmap)

    }

    override fun showDialog(user: UserVO) {
        mUserProfileDialogFragment = UserProfileDialogFragment.newFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_NAME, user.name)             //1
        bundle.putString(BUNDLE_PHONE_NUMBER, user.phone)    //2
        bundle.putString(BUNDLE_PASSWORD, user.password)     //3
        bundle.putString(BUNDLE_DATE_OF_BIRTH, user.dateOfBirth)  //4
        bundle.putString(BUNDLE_GENDER, user.gender)         //5
        bundle.putString(BUNDLE_PROFILE, user.profile)
        bundle.putString(BUNDLE_UID, user.userUID)           //6
        mUserProfileDialogFragment?.arguments = bundle

        mUserProfileDialogFragment?.show(
            parentFragmentManager,
            UserProfileDialogFragment.TAG_UPDATE_USER
        )
    }

    override fun navigateToLoginView() {

        // clear activity history
        val newIntent = Intent(requireContext(), LoginActivity::class.java)
        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(newIntent)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            val filePath = data.data
            try {

                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(requireContext().contentResolver, filePath)

                        val bitmap = ImageDecoder.decodeBitmap(source)
                        mPresenter.onPhotoTaken(bitmap, mUser)
                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            requireContext().contentResolver, filePath
                        )
                        mPresenter.onPhotoTaken(bitmap, mUser)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

//    override fun setUpUserVO(user: UserVO) {
//        mUser = user
//
//        bindMyData()
//        setUpListeners()
//    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}