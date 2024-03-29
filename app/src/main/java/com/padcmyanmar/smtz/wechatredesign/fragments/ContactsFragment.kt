package com.padcmyanmar.smtz.wechatredesign.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.activities.ChatThreadActivity
import com.padcmyanmar.smtz.wechatredesign.activities.ChatThreadGroupActivity
import com.padcmyanmar.smtz.wechatredesign.activities.NewGroupActivity
import com.padcmyanmar.smtz.wechatredesign.adapters.ContactAdapter
import com.padcmyanmar.smtz.wechatredesign.adapters.GroupAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.ContactsPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.ContactsPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ContactsView
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_moments.toolBar

class ContactsFragment : AbstractBaseFragment(), ContactsView {

    private lateinit var mPresenter: ContactsPresenter
    private var contactList = listOf<UserVO>()

    private lateinit var mUser: UserVO
    private lateinit var mGroupAdapter: GroupAdapter
    private lateinit var mContactAdapter: ContactAdapter
    private lateinit var mContact: String

    companion object {
        const val BUNDLE_USER = "BUNDLE_USER"

        fun newFragment(): ContactsFragment {
            return ContactsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mUser = arguments?.getSerializable(BUNDLE_USER) as UserVO

        setUpPresenter()
        setUpToolbar()
        setUpListeners()
        setUpAdapters()

        mPresenter.onUiReady(this, mUser.userUID!!)
    }

    override fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ContactsPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        btnAddNewGroup.setOnClickListener {
            startActivity(NewGroupActivity.newIntent(requireContext(), mUser))
        }

        btnAddNewContact.setOnClickListener {
            barcodeLauncher.launch(ScanOptions())

            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
            options.setPrompt("Scan a barcode")
            options.setCameraId(0) // Use a specific camera of the device

            options.setBeepEnabled(false)
            options.setBarcodeImageEnabled(true)
            options.setOrientationLocked(false)
            barcodeLauncher.launch(options)
        }

        val searchedContacts: MutableList<UserVO> = mutableListOf()
        textInputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchedContacts.clear()
                contactList.forEach {
                    if (s != null) {
                        if (it.name?.lowercase()?.contains(s.toString().lowercase()) == true) {
                            searchedContacts.add(it)
                        }
                    }
                }
                mContactAdapter.setNewData(searchedContacts)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    // Register the launcher and result handler
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        if (result.contents == null) {
            showError("Cancelled")
        } else {
            if(result.contents != mUser.userUID) {
                mContact = result.contents
                mPresenter.onTapAddContact(contactUID = result.contents, loggedInUser = mUser)
            } else {
                showError("Can't add yourself")
            }
        }
    }

    //App Bar Leading icon
    private fun setUpToolbar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolBar)
        toolBar.setTitleTextAppearance(requireContext(), R.style.TextAppearance)
    }

    private fun setUpAdapters() {
        mGroupAdapter = GroupAdapter(mPresenter, mUser)
        rvGroups.adapter = mGroupAdapter
        rvGroups.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mContactAdapter = ContactAdapter(mPresenter, mUser.userUID!!)
        rvContacts.adapter = mContactAdapter
        rvContacts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun showContactsData(contacts: List<UserVO>) {
        contactList = contacts
        mContactAdapter.setNewData(contacts)
    }

    override fun hideContactListView() {
        llContactView.visibility = View.GONE
    }

    override fun showContactListView() {
        llContactView.visibility = View.VISIBLE
    }

    override fun navigateToChatThread(loggedInUserUID: String, contactUID: String) {
        startActivity(ChatThreadActivity.newIntent(requireContext(), loggedInUserUID, contactUID))
    }

    override fun showGroupListUserJoined(groups: List<String>) {
        mGroupAdapter.setNewData(groups)
    }

    override fun navigateToChatThreadFromGroup(loggedInUser: UserVO, group: GroupVO) {
        startActivity(ChatThreadGroupActivity.newIntent(requireContext(), loggedInUser, group))
    }
}