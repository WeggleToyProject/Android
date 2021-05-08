package com.jinhyun.weggletoyproject.retrofit.response.edit.myuserinfo

data class EditMyUserInfoErrorResponse(
    val code: Int,
    val message: String,
    val method: String,
    val url: String
): EditMyUserInfoResponse