package com.jinhyun.weggletoyproject.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jinhyun.weggletoyproject.R
import com.jinhyun.weggletoyproject.model.UserModel
import com.jinhyun.weggletoyproject.retrofit.methods.UserAPIMethods
import com.jinhyun.weggletoyproject.retrofit.request.SignUpCheckRequest
import com.jinhyun.weggletoyproject.retrofit.response.account.signupcheck.ExistUserResponse
import com.jinhyun.weggletoyproject.utils.AccessTokenManager
import com.jinhyun.weggletoyproject.utils.EmailManager
import com.jinhyun.weggletoyproject.viewmodel.UserViewModel

class SplashActivity : AppCompatActivity() {
    private val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )

        goNextActivity()
    }

    private fun getUserViewModel(): UserViewModel {

        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(UserViewModel::class.java)
    }

    private fun goNextActivity() {

        getUserViewModel().getUser().observe(this, Observer {

            when (it) {
                null -> {
                    goLoginActivity()
                }
                else -> {
                    signIn(it)
                }
            }
        })
    }

    private fun signIn(userModel: UserModel) {
        UserAPIMethods.signUpCheck(
            SignUpCheckRequest(
                userModel.signUpType,
                userModel.socialId,
                userModel.pushToken,
                userModel.os,
                userModel.versionApp,
                userModel.email
            )
        ) { response ->

            when (response) {
                is ExistUserResponse -> {

                    AccessTokenManager(applicationContext).setToken(response.item.access_token)
                    EmailManager(applicationContext).setEmail(response.item.email)

                    goMainActivity()
                }
                else -> {
                    Toast.makeText(
                        applicationContext,
                        "알 수 없는 에러가 발생하여 로그인에 실패하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun goMainActivity() {

        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goLoginActivity() {

        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun finish() {
        super.finish()

        this.overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        );
    }
}