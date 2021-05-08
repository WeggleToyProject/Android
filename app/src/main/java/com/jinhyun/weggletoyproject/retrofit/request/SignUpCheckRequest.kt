package com.jinhyun.weggletoyproject.retrofit.request

data class SignUpCheckRequest(
    val signUpType: String,
    val socialId: String,
    val pushToken: String,
    val os: String,
    val versionApp: String,
    val email: String
)