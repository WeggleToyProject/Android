package com.jinhyun.weggletoyproject.view

import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jinhyun.weggletoyproject.R
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.activity_terms_of_use.*

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        setSupportActionBar(toolbar_privacy_policy)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setView()

        initBackButton()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setView() {
        wv_privacy_policy.webViewClient

        val webViewSettings = wv_privacy_policy.settings
        webViewSettings.javaScriptEnabled = true
        webViewSettings.setSupportMultipleWindows(false)
        webViewSettings.javaScriptCanOpenWindowsAutomatically = false
        webViewSettings.loadWithOverviewMode = true
        webViewSettings.useWideViewPort = false
        webViewSettings.setSupportZoom(true)
        webViewSettings.builtInZoomControls = true
        webViewSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webViewSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webViewSettings.domStorageEnabled = true

        wv_privacy_policy.loadUrl("http://weggle.kr/terms/privacy.html")

    }

    private fun initBackButton() {
        ibtn_toolbar_privacy_policy_arrow_back.setOnClickListener {
            finish()
        }
    }
}