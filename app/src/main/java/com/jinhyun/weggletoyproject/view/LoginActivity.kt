package com.jinhyun.weggletoyproject.view

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jinhyun.weggletoyproject.R
import com.jinhyun.weggletoyproject.retrofit.methods.UserAPIMethods
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setView()
        initAuthButton()
    }

    private fun setView() {

        setAgreeTextView()
    }

    private fun setAgreeTextView() {

        val description: String = resources.getString(R.string.agree_if_login)

        val privacyPolicyWord = "개인정보처리방침"
        val privacyPolicySpanStart = description.indexOf(privacyPolicyWord)
        val privacyPolicySpanEnd = privacyPolicySpanStart + privacyPolicyWord.length

        val termsOfUseWord = "이용약관"
        val termsOfUseSpanStart = description.indexOf(termsOfUseWord)
        val termsOfUseSpanEnd = termsOfUseSpanStart + termsOfUseWord.length

        val ssb = SpannableStringBuilder(description)

        val privacyPolicySpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                startActivity(Intent(applicationContext, PrivacyPolicyActivity::class.java))
            }
        }

        val termsOfUseSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                startActivity(Intent(applicationContext, TermsOfUseActivity::class.java))
            }
        }

        ssb.setSpan(
            privacyPolicySpan,
            privacyPolicySpanStart,
            privacyPolicySpanEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.setSpan(
            termsOfUseSpan,
            termsOfUseSpanStart,
            termsOfUseSpanEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_agree_if_login.text = ssb
        tv_agree_if_login.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun initAuthButton() {
        kakao_auth_button.setOnClickListener {
            val signUpType = "kakao"
            val socialId = "kakao_1234"
            val pushToken: String = socialId
            val os = "android"
            val versionApp = "0.0.1"

            goSignUpPage(signUpType, socialId, pushToken, os, versionApp)
        }

        naver_auth_button.setOnClickListener {
            val signUpType = "naver"
            val socialId = "naver_1234"
            val pushToken: String = socialId
            val os = "android"
            val versionApp = "0.0.1"

            goSignUpPage(signUpType, socialId, pushToken, os, versionApp)
        }
    }

    private fun goSignUpPage(
        signUpType: String,
        socialId: String,
        pushToken: String,
        os: String,
        versionApp: String
    ) {

        val intent = Intent(
            applicationContext,
            InterestSelectionActivity::class.java
        )

        val signUpForm = Bundle()
        signUpForm.putString("signup_type", signUpType)
        signUpForm.putString("social_id", socialId)
        signUpForm.putString("push_token", pushToken)
        signUpForm.putString("os", os)
        signUpForm.putString("version_app", versionApp)
        intent.putExtra("signup_form", signUpForm)
        startActivity(intent)
    }
}