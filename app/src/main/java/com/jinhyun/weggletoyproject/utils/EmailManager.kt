package com.jinhyun.weggletoyproject.utils

import android.content.Context
import android.content.SharedPreferences
import com.jinhyun.weggletoyproject.R

class EmailManager(private val context: Context) {
    val defaultValue: String = ""

    val prefs: SharedPreferences = context.getSharedPreferences(
        context.resources.getString(R.string.prefs_email),
        Context.MODE_PRIVATE
    )

    fun setEmail(accessToken: String) {
        val editor: SharedPreferences.Editor = getEditor()
        editor.putString(context.resources.getString(R.string.email), accessToken)
        editor.commit()
    }

    fun getEmail(): String? {
        return prefs.getString(context.resources.getString(R.string.email), defaultValue)
    }

    private fun getEditor(): SharedPreferences.Editor {
        return prefs.edit()
    }
}