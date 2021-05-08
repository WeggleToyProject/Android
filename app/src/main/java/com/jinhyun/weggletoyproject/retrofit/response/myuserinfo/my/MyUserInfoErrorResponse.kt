package com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my

data class MyUserInfoErrorResponse(
    val code: Int,
    val message: String,
    val method: String,
    val url: String
): MyUserInfoResponse