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
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.MessageAdapter
import com.padcmyanmar.smtz.wechatredesign.adapters.SelectedMessagePhotoAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.ChatThreadGroupPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.ChatThreadGroupPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ChatThreadGroupView
import com.padcmyanmar.smtz.wechatredesign.utils.loadBitmapFromUri
import com.padcmyanmar.smtz.wechatredesign.utils.scaleToRatio
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.activity_chat_thread.*

class ChatThreadGroupActivity : AbstractBaseActivity(), ChatThreadGroupView {

    private lateinit var mPresenter: ChatThreadGroupPresenter

    private lateinit var mMessagesAdapter: MessageAdapter
    private lateinit var mSelectedPhotoAdapter: SelectedMessagePhotoAdapter

    private lateinit var mCurrentUser: UserVO
    private lateinit var mContact: UserVO
    private lateinit var mGroup: GroupVO
    private var mChosenImagesBitmap: ArrayList<Bitmap> = arrayListOf()

    companion object {
        const val PICK_IMAGE_REQUEST = 1111

        private const val EXTRA_CURRENT_USER = "EXTRA_CURRENT_USER"
        private const val EXTRA_GROUP = "EXTRA_GROUP"

        fun newIntent(context: Context, currentUser: UserVO, group: GroupVO): Intent {
            val intent = Intent(context, ChatThreadGroupActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER, currentUser)
            intent.putExtra(EXTRA_GROUP, group)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_thread)
        setUpPresenter()

        mCurrentUser = intent.getSerializableExtra(EXTRA_CURRENT_USER) as UserVO
        mGroup = intent.getSerializableExtra(EXTRA_GROUP) as GroupVO

        setUpListeners()
        setUpAdapters()
        bindData()

        mPresenter.onUiReady(this, mGroup)
    }

    override fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ChatThreadGroupPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnBackChatThread.setOnClickListener { finish() }

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
                    groupName = mGroup.groupName,
                    message = message,
                    senderUID = mCurrentUser.userUID!!,
                    senderName = mCurrentUser.name ?: "",
                    senderProfile = mCurrentUser.profile ?: "",
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
        mMessagesAdapter = MessageAdapter(mCurrentUser.userUID!!)
        rvChatMessages.adapter = mMessagesAdapter
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            .apply {
                stackFromEnd = false                  // items gravity sticks to bottom
                reverseLayout = true                // item list sorting from the bottom (messenger)
                rvChatMessages.layoutManager = this
            }

        mSelectedPhotoAdapter = SelectedMessagePhotoAdapter()
        rvSelectedPhotosMessage.adapter = mSelectedPhotoAdapter
        rvSelectedPhotosMessage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    override fun showGroupMessages(messages: List<MessageVO>) {
        mMessagesAdapter.setNewData(messages)
    }

    private fun bindData() {
//        Glide.with(this)
//            .load(mContactVO.profile)
//            .into(ivFriendProfile)
        tvFriendName.text = mGroup.groupName
    }


//    override fun setUpCurrentUserVO(currentUserVO: UserVO) {
//        mCurrentUserVO = currentUserVO
//    }
//
//    override fun setUpContactVO(contactVO: UserVO) {
//        mContactVO = contactVO
//        bindData()
//    }
//
//    override fun showMessages(messages: List<MessageVO>) {
//        mMessagesAdapter.setNewData(messages)
//    }

}