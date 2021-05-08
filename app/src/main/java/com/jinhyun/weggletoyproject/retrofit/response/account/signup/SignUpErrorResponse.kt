package com.jinhyun.weggletoyproject.retrofit.response.account.signup

data class SignUpErrorResponse(
    val code: Int,
    val message: String,
    val method: String,
    val url: String
): SignUpResponse