package com.padcmyanmar.smtz.wechatredesign.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.SelectedPhotoAdapter
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModel
import com.padcmyanmar.smtz.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.fragments.MyFragment
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.NewMomentPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.NewMomentPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewMomentView
import kotlinx.android.synthetic.main.activity_new_moment.*
import java.io.IOException

class NewMomentActivity : AppCompatActivity(), NewMomentView {

    private lateinit var mPresenter: NewMomentPresenter

    private lateinit var mSelectedPhotoAdapter: SelectedPhotoAdapter
    private var selectedPhotoList: ArrayList<Bitmap> = arrayListOf()
    private var photoListString: ArrayList<String> = arrayListOf()
    private var mUser = UserVO()
    private val millis = System.currentTimeMillis()

    private var mUserModel: UserModel = UserModelImpl

    companion object {
        const val PICK_IMAGE_LIST_REQUEST = 1111

        private const val EXTRA_USER = "EXTRA_USER"

        fun newIntent(context: Context, userVO: UserVO): Intent {
            val intent = Intent(context, NewMomentActivity::class.java)
            intent.putExtra(EXTRA_USER, userVO);
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_moment)
        setUpPresenter()

        mUser = intent.getSerializableExtra(EXTRA_USER) as UserVO

        setUpToolbar()
        setUpListeners()
        setUpAdapter()
        bindMyData()

        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[NewMomentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun bindMyData() {
        tvUserNameNewMoments.text = mUser.name

        if(mUser.profile != ""){
            Glide.with(this)
                .load(mUser.profile)
                .into(ivProfileNewMoments)
        }
    }

    private fun setUpListeners() {
        btnClose.setOnClickListener {
            super.onBackPressed()
        }

        btnSelectPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_IMAGE_LIST_REQUEST)
        }

        btnCreate.setOnClickListener {

            val content = etCreateMoment.text.toString().trim()

            if(etCreateMoment.text.toString().trim().isNotEmpty() && photoListString.isNotEmpty()) {
                mPresenter.onTapCreate(
                    millis = millis,
                    likeCount = "0",
                    content = content,
                    user = mUser.userUID!!,
                    userName = mUser.name!!,                // didn't reuse in momentVH as this is static
                    userProfile = mUser.profile ?: "",      // didn't reuse in momentVH as this is static
                    photoListString = photoListString
                )
            }
            onBackPressed()
        }
    }

    private fun setUpAdapter() {
        mSelectedPhotoAdapter = SelectedPhotoAdapter()
        rvSelectedPhotos.adapter = mSelectedPhotoAdapter
        rvSelectedPhotos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolBar)
        toolBar.setTitleTextAppearance(this, R.style.TextAppearanceCreateMoment)
    }

    override fun setUpImageStringArray(imageString: String) {
        photoListString.add(imageString)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MyFragment.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            val filePath = data.data
            try {
                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source = ImageDecoder.createSource(this.contentResolver, filePath)
                        val bitmap = ImageDecoder.decodeBitmap(source)

                        selectedPhotoList.add(bitmap)

                        mUserModel.uploadPhotoToFirestoreAndReturnString(
                            bitmap,
                            onSuccess = {
                                photoListString.add(it)
                                mSelectedPhotoAdapter.setNewData(photoListString)

                            })

                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            this.contentResolver, filePath
                        )
                        selectedPhotoList.add(bitmap)

                        mUserModel.uploadPhotoToFirestoreAndReturnString(
                            bitmap,
                            onSuccess = {
                                photoListString.add(it)
                                mSelectedPhotoAdapter.setNewData(photoListString)
                            })
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun showError(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
    }


}