package com.jinhyun.weggletoyproject.utils

import android.content.Context
import android.content.SharedPreferences
import com.jinhyun.weggletoyproject.R

class AccessTokenManager(private val context: Context) {

    val defaultValue: String = ""

    val prefs: SharedPreferences = context.getSharedPreferences(
        context.resources.getString(R.string.prefs_access_token),
        Context.MODE_PRIVATE
    )

    fun setToken(accessToken: String) {
        val editor: SharedPreferences.Editor = getEditor()
        editor.putString(context.resources.getString(R.string.access_token), accessToken)
        editor.commit()
    }

    fun getToken(): String? {
        return prefs.getString(context.resources.getString(R.string.access_token), defaultValue)
    }

    private fun getEditor(): SharedPreferences.Editor {
        return prefs.edit()
    }
}