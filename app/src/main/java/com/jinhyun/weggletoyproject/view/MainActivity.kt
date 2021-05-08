package com.jinhyun.weggletoyproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import coil.api.load
import com.jinhyun.weggletoyproject.R
import com.jinhyun.weggletoyproject.constants.Constants
import com.jinhyun.weggletoyproject.retrofit.methods.UserAPIMethods
import com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my.MyUserInfoErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my.MyUserInfoItem
import com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my.MyUserInfoOkResponse
import com.jinhyun.weggletoyproject.utils.AccessTokenManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var myUserInfo: MyUserInfoItem? = null

    companion object {
        private const val REQUEST_EDIT_PROFILE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadView()
    }

    private fun loadView() {
        showLoadingPanel()

        UserAPIMethods.myUserInfo(AccessTokenManager(applicationContext).getToken()!!){ response ->
            when(response){
                is MyUserInfoOkResponse -> {

                    myUserInfo = response.item

                    setView()
                    mapMyUserInfoToViews()
                }
                is MyUserInfoErrorResponse -> {
                    Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(applicationContext, "정보 불러오기 실패", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun setView() {
        ibtn_edit_my_profile?.setOnClickListener {
            startActivityForResult(
                Intent(applicationContext, EditProfileActivity::class.java),
                REQUEST_EDIT_PROFILE
            )
        }
        ibtn_my_setting.setOnClickListener {
            startActivityForResult(
                Intent(applicationContext, EditProfileActivity::class.java),
                REQUEST_EDIT_PROFILE
            )
        }
    }

    private fun mapMyUserInfoToViews() {
        val info: MyUserInfoItem = myUserInfo!!

        val profileImageUrl = "${Constants.contentsDomain}/${info.filename}"

        civ_profile?.load(profileImageUrl)
        tv_nickname_profile?.text = info.nickname
        tv_description_profile?.text = info.about
    }

    private fun showLoadingPanel() {
        loading_panel_fragment_profile?.visibility = View.VISIBLE
    }

    private fun hideLoadingPanel() {
        loading_panel_fragment_profile?.visibility = View.INVISIBLE
    }

}