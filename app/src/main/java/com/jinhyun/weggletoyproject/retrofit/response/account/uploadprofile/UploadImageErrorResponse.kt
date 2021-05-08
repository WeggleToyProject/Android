package com.jinhyun.weggletoyproject.retrofit.response.account.uploadprofile

data class UploadImageErrorResponse(
    val code: Int,
    val message: String,
    val method: String,
    val url: String
): UploadImageResponse