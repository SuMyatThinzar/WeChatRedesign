package com.padcmyanmar.smtz.wechatredesign.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.CheckContactsAdapter
import com.padcmyanmar.smtz.wechatredesign.adapters.SelectedContactsAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.NewGroupPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.NewGroupPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewGroupView
import kotlinx.android.synthetic.main.activity_new_group.*

class NewGroupActivity : AppCompatActivity(), NewGroupView {

    private lateinit var mPresenter: NewGroupPresenter

    private lateinit var mUser: UserVO
    private lateinit var mCheckContactAdapter: CheckContactsAdapter
    private lateinit var mSelectedContactsAdapter: SelectedContactsAdapter

    private var members: MutableList<UserVO> = mutableListOf()
    private var mContacts: List<UserVO> = listOf()

    companion object {
        const val PICK_IMAGE_LIST_REQUEST = 1111

        private const val EXTRA_USER = "EXTRA_USER"

        fun newIntent(context: Context, userVO: UserVO): Intent {
            val intent = Intent(context, NewGroupActivity::class.java)
            intent.putExtra(EXTRA_USER, userVO);
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)
        setUpPresenter()

        mUser = intent.getSerializableExtra(EXTRA_USER) as UserVO

        setUpListeners()
        setUpAdapters()

        mPresenter.onUiReady(this, mUser.userUID!!)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[NewGroupPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){

        btnCreate.setOnClickListener {
            val membersUids: MutableList<String> = mutableListOf()
            members.add(mUser)
            members.reversed().forEach {
                membersUids.add(it.userUID!!)
            }

            if (etGroupName.text?.trim().toString() != "" && members.size >= 3) {
                mPresenter.onTapCreate(etGroupName.text?.trim().toString(), membersUids)
            }
            onBackPressed()
            Toast.makeText(this, "Group Created Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpAdapters() {

        mSelectedContactsAdapter = SelectedContactsAdapter()
        rvSelectedContactsNewGroup.adapter = mSelectedContactsAdapter
        rvSelectedContactsNewGroup.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mCheckContactAdapter = CheckContactsAdapter(mPresenter)
        rvCheckContactsNewGroup.adapter = mCheckContactAdapter
        rvCheckContactsNewGroup.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun showContactList(contacts: List<UserVO>) {
        mContacts = contacts
        mCheckContactAdapter.setNewData(contacts)
    }

    override fun showEmptyView() {
        emptyViewContacts.visibility = View.VISIBLE
        llContactListView.visibility = View.GONE
    }

    override fun hideEmptyView() {
        emptyViewContacts.visibility = View.GONE
        llContactListView.visibility = View.VISIBLE
    }

    override fun setSelectedContacts(contact: UserVO) {
        if (members.isNotEmpty()) {
            var temp = true
            run loop@{
                members.forEach{
                    if (it == contact) {
                        temp = false
                        return@loop
                    } else  temp = true
                }
            }

            when (temp) {
                true -> members.add(contact)
                false -> members.remove(contact)
            }

        } else  members.add(contact)

        mSelectedContactsAdapter.setNewData(members)

        //change check button
        if(mContacts.isNotEmpty()) {
            mContacts.forEach {
                if(it == contact) {
                    it.selected = it.selected == false
                }
            }
        }
        mCheckContactAdapter.setNewData(mContacts)
    }

    override fun showError(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
    }
}