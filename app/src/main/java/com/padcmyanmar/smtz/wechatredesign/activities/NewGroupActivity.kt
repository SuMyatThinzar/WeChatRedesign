package com.padcmyanmar.smtz.wechatredesign.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.adapters.CheckContactsAdapter
import com.padcmyanmar.smtz.wechatredesign.adapters.SelectedContactsAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.NewGroupPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.NewGroupPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.NewGroupView
import kotlinx.android.synthetic.main.activity_new_group.*
import kotlinx.android.synthetic.main.fragment_contacts.textInputSearch

class NewGroupActivity : AbstractBaseActivity(), NewGroupView {

    private lateinit var mPresenter: NewGroupPresenter

    private lateinit var mUser: UserVO
    private lateinit var mCheckContactAdapter: CheckContactsAdapter
    private lateinit var mSelectedContactsAdapter: SelectedContactsAdapter

    private var mSelectedMomebers: MutableList<UserVO> = mutableListOf()
    private var mContacts: List<UserVO> = listOf()
    private var mSelectedContactList: MutableList<UserVO> = mutableListOf()

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

    override fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[NewGroupPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){

        btnClose.setOnClickListener {
            finish()
        }

        btnCreate.setOnClickListener {
            val membersUids: MutableList<String> = mutableListOf()
            mSelectedMomebers.add(mUser)
            mSelectedMomebers.reversed().forEach {
                membersUids.add(it.userUID!!)
            }

            if (mSelectedMomebers.size >= 3) {
                mPresenter.onTapCreate(etGroupName.text?.trim().toString(), membersUids)
                Toast.makeText(applicationContext, "Group Created Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        etGroupName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    checkValidity()
                } else {
                    makeButtonInactive()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val searchedContacts: MutableList<UserVO> = mutableListOf()
        textInputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchedContacts.clear()
                mContacts.forEach {
                    if (s != null) {
                        if (it.name?.lowercase()?.contains(s.toString().lowercase()) == true) {
                            searchedContacts.add(it)
                        }
                    }
                }
                mCheckContactAdapter.setNewData(searchedContacts)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setUpAdapters() {

        mSelectedContactsAdapter = SelectedContactsAdapter()
        rvSelectedContactsNewGroup.adapter = mSelectedContactsAdapter
        rvSelectedContactsNewGroup.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mCheckContactAdapter = CheckContactsAdapter(mPresenter)
        rvContactList.adapter = mCheckContactAdapter
        rvContactList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun checkValidity() {
        if (mSelectedMomebers.count() > 1 && etGroupName.text?.trim().toString() != "")  makeButtonActive()
        else makeButtonInactive()
    }

    private fun makeButtonInactive() {
        btnCreate.background = ContextCompat.getDrawable(applicationContext, R.drawable.background_button_inactive)
        btnCreate.isEnabled = false
    }

    private fun makeButtonActive() {
        btnCreate.background = ContextCompat.getDrawable(applicationContext, R.drawable.background_button_active_accent)
        btnCreate.isEnabled = true
    }

    override fun showContactList(contacts: List<UserVO>) {
        mContacts = contacts
        mCheckContactAdapter.setNewData(contacts)
    }

    override fun showSelectedContactList(contacts: List<UserVO>) {
        mSelectedMomebers = contacts.toMutableList()
        mSelectedContactsAdapter.setNewData(contacts) // from singleton data setup from impl
    }

    override fun showEmptyView() {
        emptyViewContacts.visibility = View.VISIBLE
        llContactListView.visibility = View.GONE
    }

    override fun hideEmptyView() {
        emptyViewContacts.visibility = View.GONE
        llContactListView.visibility = View.VISIBLE
    }
}