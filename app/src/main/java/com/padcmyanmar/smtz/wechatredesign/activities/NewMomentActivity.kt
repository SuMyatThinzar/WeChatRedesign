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
import android.view.inputmethod.InputMethodManager
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

class NewMomentActivity : AbstractBaseActivity(), NewMomentView {

    private lateinit var mPresenter: NewMomentPresenter

    private lateinit var mSelectedPhotoAdapter: SelectedPhotoAdapter
    private var selectedPhotoBitmapList: ArrayList<Bitmap> = arrayListOf()
    private var photoListString: ArrayList<String> = arrayListOf()
    private var mUser = UserVO()
    private val millis = System.currentTimeMillis()

    private var shouldAllowBackPressed: Boolean = true

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

    override fun setUpPresenter() {
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
            finish()
        }

        btnSelectPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_IMAGE_LIST_REQUEST)
        }

        btnCreate.setOnClickListener {
            // hide keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)

            val content = etCreateMoment.text.toString().trim()

            if(etCreateMoment.text.toString().trim().isNotEmpty() || selectedPhotoBitmapList.isNotEmpty()) {
                mPresenter.onTapCreate(
                    millis = millis,
                    likeCount = "0",
                    content = content,
                    user = mUser.userUID!!,
                    userName = mUser.name!!,                // didn't reuse in momentVH as this is static
                    userProfile = mUser.profile ?: "",      // didn't reuse in momentVH as this is static
                    photoListBitmap = selectedPhotoBitmapList
                )
            }
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

    private fun setUpViewClickable(isClickable: Boolean) {
        shouldAllowBackPressed = isClickable
        btnClose.isClickable = isClickable
        btnCreate.isClickable = isClickable
        etCreateMoment.isFocusable = isClickable
        btnSelectPhoto.isClickable = isClickable
    }


    override fun setUpImageStringArray(imageString: String) {
        photoListString.add(imageString)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        setUpViewClickable(false)
    }
    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
        setUpViewClickable(true)
    }

    override fun finishActivity() {
        finish()
    }
    override fun onBackPressed() {
        if (shouldAllowBackPressed) {
            super.onBackPressed()
        }
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

                        selectedPhotoBitmapList.add(bitmap)

                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            this.contentResolver, filePath
                        )
                        selectedPhotoBitmapList.add(bitmap)
                    }
                    mSelectedPhotoAdapter.setNewData(selectedPhotoBitmapList)
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}