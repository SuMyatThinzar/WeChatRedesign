package com.padcmyanmar.smtz.wechatredesign.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.smtz.wechatredesign.R
import com.padcmyanmar.smtz.wechatredesign.data.vos.UserVO
import com.padcmyanmar.smtz.wechatredesign.fragments.*
import com.padcmyanmar.smtz.wechatredesign.fragments.ContactsFragment.Companion.BUNDLE_USER
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.MainScreenPresenter
import com.padcmyanmar.smtz.wechatredesign.mvp.presenters.activityPresenters.MainScreenPresenterImpl
import com.padcmyanmar.smtz.wechatredesign.mvp.views.MainScreenView
import kotlinx.android.synthetic.main.activity_main_screen.*


class MainScreenActivity : AbstractBaseActivity(), MainScreenView {

    private lateinit var mPresenter: MainScreenPresenter

    lateinit var uid: String
    private lateinit var mUserVO: UserVO

    var previousIndex : Int = 0

    companion object {

        const val EXTRA_USER_UID = "EXTRA USER UID"

        fun newIntent(context: Context, uid: String): Intent {
            val intent = Intent(context, MainScreenActivity::class.java)
            intent.putExtra(EXTRA_USER_UID, uid)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        setUpPresenter()

        intent.getStringExtra(EXTRA_USER_UID)?.let { uid = it }

        mPresenter.onUiReady(this, uid)
    }

    override fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MainScreenPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    override fun setUpUserVO(user: UserVO) {
        mUserVO = user
        setUpBottomNavigation(user, previousIndex)
    }

    private fun setUpBottomNavigation(user: UserVO, index: Int) {

        val fragment1: Fragment = MomentsFragment(user)
        val fragment2: Fragment = ChatFragment(user)
        val fragment3: Fragment = ContactsFragment.newFragment()
        val bundle = Bundle()
        bundle.putSerializable(BUNDLE_USER, user)
        fragment3.arguments = bundle

        val fragment4: Fragment = MyFragment(user)
        val fragment5: Fragment = SettingFragment(user)
        val fm = supportFragmentManager
        var previousFragment = fragment1

        fm.beginTransaction().add(R.id.fl_container, fragment5, "5").hide(fragment5).commit()
        fm.beginTransaction().add(R.id.fl_container, fragment4, "4").hide(fragment4).commit()
        fm.beginTransaction().add(R.id.fl_container, fragment3, "3").hide(fragment3).commit()
        fm.beginTransaction().add(R.id.fl_container, fragment2, "2").hide(fragment2).commit()
        fm.beginTransaction().add(R.id.fl_container, fragment1, "1").commit()

        when (index) {
            0 -> {
                fm.beginTransaction().hide(previousFragment).show(fragment1).commit()
                previousFragment = fragment1
            }
            1 -> {
                fm.beginTransaction().hide(previousFragment).show(fragment2).commit()
                previousFragment = fragment2
            }
            2 -> {
                fm.beginTransaction().hide(previousFragment).show(fragment3).commit()
                previousFragment = fragment3
            }
            3 -> {
                fm.beginTransaction().hide(previousFragment).show(fragment4).commit()
                previousFragment = fragment4
            }
            4 -> {
                fm.beginTransaction().hide(previousFragment).show(fragment5).commit()
                previousFragment = fragment5
            }
        }


        bottom_nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_moment -> {
                    fm.beginTransaction().hide(previousFragment).show(fragment1).commit()
                    previousFragment = fragment1
                    previousIndex = 0
                }
                R.id.action_chat -> {
                    fm.beginTransaction().hide(previousFragment).show(fragment2).commit()
                    previousFragment = fragment2
                    previousIndex = 1
                }
                R.id.action_contact -> {
                    fm.beginTransaction().hide(previousFragment).show(fragment3).commit()
                    previousFragment = fragment3
                    previousIndex = 2
                }
                R.id.action_me -> {
                    fm.beginTransaction().hide(previousFragment).show(fragment4).commit()
                    previousFragment = fragment4
                    previousIndex = 3
                }
                R.id.action_setting -> {
                    fm.beginTransaction().hide(previousFragment).show(fragment5).commit()
                    previousFragment = fragment5
                    previousIndex = 4
                }
            }
            true
        }
    }


//    private fun setUpBottomNavigation(user: UserVO) {
//
//        switchFragment(MomentsFragment(user))
//        bottom_nav.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.action_moment -> switchFragment(MomentsFragment(user))
//                R.id.action_chat -> switchFragment(ChatFragment(user))
//                R.id.action_contact -> {
//                    val contactsFragment = ContactsFragment.newFragment()
//                    val bundle = Bundle()
//                    bundle.putSerializable(BUNDLE_USER, user)
//                    contactsFragment.arguments = bundle
//                    switchFragment(contactsFragment)
//                }
//                R.id.action_me -> switchFragment(MyFragment(user))
//                R.id.action_setting -> switchFragment(SettingFragment(user))
//            }
//            true
//        }
//    }
//
//    private fun switchFragment(fragment: Fragment) {
//
//        if (!supportFragmentManager.isDestroyed) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fl_container, fragment)
//                .commit()
//        }
//    }

}