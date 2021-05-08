package com.jinhyun.weggletoyproject.retrofit.response.account.signupcheck

data class NewUserOrErrorResponse(
    val code: Int,
    val message: String,
    val method: String,
    val url: String
) : SignUpCheckResponse