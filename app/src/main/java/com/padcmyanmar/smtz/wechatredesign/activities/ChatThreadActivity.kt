package com.padcmyanmar.smtz.wechatredesign.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.MessageAdapter
import com.padcmyanmar.smtz.wechatredesign.adapters.SelectedMessagePhotoAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.ChatThreadPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.ChatThreadPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ChatThreadView
import com.padcmyanmar.smtz.wechatredesign.utils.loadBitmapFromUri
import com.padcmyanmar.smtz.wechatredesign.utils.scaleToRatio
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.activity_chat_thread.*

class ChatThreadActivity : AppCompatActivity(), ChatThreadView {

    private lateinit var mPresenter: ChatThreadPresenter

    private lateinit var mMessagesAdapter: MessageAdapter
    private lateinit var mSelectedPhotoAdapter: SelectedMessagePhotoAdapter

    private lateinit var currentUser: String
    private lateinit var contact: String
    private lateinit var mCurrentUserVO: UserVO
    private lateinit var mContactVO: UserVO
    private var mChosenImagesBitmap: ArrayList<Bitmap> = arrayListOf()

    companion object {
        const val PICK_IMAGE_REQUEST = 1111

        private const val EXTRA_CURRENT_USER = "EXTRA_CURRENT_USER"
        private const val EXTRA_CONTACT = "EXTRA_CONTACT"

        fun newIntent(context: Context, currentUser: String, contact: String): Intent {
            val intent = Intent(context, ChatThreadActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER, currentUser)
            intent.putExtra(EXTRA_CONTACT, contact)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_thread)
        setUpPresenter()

        currentUser = intent.getStringExtra(EXTRA_CURRENT_USER).toString()
        contact = intent.getStringExtra(EXTRA_CONTACT).toString()

        setUpListeners()
        setUpAdapters()

        mPresenter.onUiReady(this, currentUser, contact)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ChatThreadPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnBackChatThread.setOnClickListener { super.onBackPressed() }

        ivSendPicture.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnSend.setOnClickListener {
            val millis = System.currentTimeMillis()
            val message = etMessage.text.trim().toString()

            if (message != "" || mChosenImagesBitmap.isNotEmpty()) {
                mPresenter.onTapSend(
                    millis = millis,
                    firstPersonUID = currentUser,
                    secondPersonUID = contact,
                    message = message,
                    senderUID = mCurrentUserVO.userUID!!,
                    senderName = mCurrentUserVO.name ?: "",
                    senderProfile = mCurrentUserVO.profile ?: "",
                    file = mChosenImagesBitmap
                )
                etMessage.text = null
                mChosenImagesBitmap = arrayListOf()
                mSelectedPhotoAdapter.setNewData(mChosenImagesBitmap)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            val imageUri: Uri? = data?.data

            imageUri?.let{ image ->
                Observable.just(image)
                    .map { it.loadBitmapFromUri(applicationContext) }
                    .map { it.scaleToRatio(0.35) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{
                        mChosenImagesBitmap.add(it)

//                        rvSelectedPhotosMessage.visibility = View.VISIBLE
                        if(mChosenImagesBitmap.isNotEmpty()) {
                            mSelectedPhotoAdapter.setNewData(mChosenImagesBitmap)
                        }
                    }
            }
        }
    }

    private fun setUpAdapters() {
        mMessagesAdapter = MessageAdapter(currentUser)
        rvChatMessages.adapter = mMessagesAdapter
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            .apply {
                stackFromEnd = false                  // items gravity sticks to bottom
                reverseLayout = true                  // item list sorting from the bottom (messenger)
                rvChatMessages.layoutManager = this
            }

        mSelectedPhotoAdapter = SelectedMessagePhotoAdapter()
        rvSelectedPhotosMessage.adapter = mSelectedPhotoAdapter
        rvSelectedPhotosMessage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

//        rvSelectedPhotosMessage.visibility = View.INVISIBLE
    }

    private fun bindData() {
        Glide.with(this)
            .load(mContactVO.profile)
            .into(ivFriendProfile)
        tvFriendName.text = mContactVO.name
    }


    override fun setUpCurrentUserVO(currentUserVO: UserVO) {
        mCurrentUserVO = currentUserVO
    }

    override fun setUpContactVO(contactVO: UserVO) {
        mContactVO = contactVO
        bindData()
    }

    override fun showMessages(messages: List<MessageVO>) {
        mMessagesAdapter.setNewData(messages)
    }

    override fun showError(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
    }
}