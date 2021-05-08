package com.jinhyun.weggletoyproject.retrofit.request

class SignUpRequest(
    private val signUpType: String,
    private val socialId: String,
    private val email: String,
    private val nickname: String,
    private val about: String,
    private val interests: Int,
    private val age: Int,
    private val gender: String,
    private val pushToken: String,
    private val os: String,
    private val versionApp: String,
    var filename: String = ""
) {
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "signup_type" to signUpType,
            "social_id" to socialId,
            "email" to email,
            "nickname" to nickname,
            "about" to about,
            "interests" to interests,
            "age" to age,
            "gender" to gender,
            "filename" to filename,
            "push_token" to pushToken,
            "os" to os,
            "version_app" to versionApp
        )
    }
}