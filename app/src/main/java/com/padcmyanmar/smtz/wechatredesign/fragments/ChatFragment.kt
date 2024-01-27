package com.padcmyanmar.smtz.wechatredesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.activities.ChatThreadActivity
import com.padcmyanmar.smtz.wechatredesign.activities.ChatThreadGroupActivity
import com.padcmyanmar.smtz.wechatredesign.adapters.ActivePeopleAdapter
import com.padcmyanmar.smtz.wechatredesign.adapters.ChatLastMessageAdapter
import com.padcmyanmar.smtz.wechatredesign.adapters.GroupLastMessageAdapter
import com.padcmyanmar.smtz.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.ChatPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.fragmentPresenters.ChatPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.ChatView
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_moments.toolBar

class ChatFragment(private var user: UserVO) : Fragment(), ChatView {

    private lateinit var mPresenter: ChatPresenter

    private lateinit var mActivePeopleAdapter: ActivePeopleAdapter
    private lateinit var mChatLastMessageAdapter: ChatLastMessageAdapter
    private lateinit var mGroupLastMessageAdapter: GroupLastMessageAdapter

    private var contactList = listOf<UserVO>()
    private var messageList = listOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setUpPresenter()
        setUpToolbar()
        setUpListeners()
        setUpAdapters()

        mPresenter.onUiReady(this, user.userUID!!)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ChatPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {

    }

    //App Bar Leading icon
    private fun setUpToolbar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolBar)
        toolBar.setTitleTextAppearance(requireContext(), R.style.TextAppearance)
    }

    private fun setUpAdapters() {
        mActivePeopleAdapter = ActivePeopleAdapter(mPresenter, user.userUID!!)
        rvActivePeople.adapter = mActivePeopleAdapter
        rvActivePeople.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mChatLastMessageAdapter = ChatLastMessageAdapter(mPresenter, user.userUID!!)
        rvChats.adapter = mChatLastMessageAdapter
        rvChats.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        mGroupLastMessageAdapter = GroupLastMessageAdapter(mPresenter, user)
        rvGroupChat.adapter = mGroupLastMessageAdapter
        rvGroupChat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun showContactsData(contacts: List<UserVO>) {
        contactList = contacts

        if (contacts.isNotEmpty()) {
            emptyViewChat.visibility = View.GONE
            mActivePeopleAdapter.setNewData(contacts)
        } else {
            emptyViewChat.visibility = View.VISIBLE
        }
    }
    override fun setUpMessages(messagedContacts: List<String>) {
        messageList = messagedContacts
        mChatLastMessageAdapter.setNewData(messagedContacts)
    }

    override fun showGroupListUserJoined(groupsUserJoined: MutableSet<String>) {
        if (groupsUserJoined.isNotEmpty()) {
            tvGroupsTitle.visibility = View.VISIBLE
            rvGroupChat.visibility = View.VISIBLE
            mGroupLastMessageAdapter.setNewData(groupsUserJoined)
        } else {
            tvGroupsTitle.visibility = View.GONE
            rvGroupChat.visibility = View.GONE
        }
    }

    override fun navigateToChatThread(loggedInUserUID: String, contactUID: String) {
        startActivity(ChatThreadActivity.newIntent(requireContext(), loggedInUserUID, contactUID))
    }

    override fun navigateToChatThreadFromGroup(loggedInUser: UserVO, group: GroupVO) {
        startActivity(ChatThreadGroupActivity.newIntent(requireContext(), loggedInUser, group))
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}