package com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my

data class MyUserInfoOkResponse(
    val item: MyUserInfoItem,
    val method: String,
    val url: String
): MyUserInfoResponse