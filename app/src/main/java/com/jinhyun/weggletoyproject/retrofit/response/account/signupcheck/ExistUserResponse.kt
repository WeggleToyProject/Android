package com.jinhyun.weggletoyproject.retrofit.response.account.signupcheck

data class ExistUserResponse(
    val item: Item,
    val method: String,
    val url: String
) : SignUpCheckResponse