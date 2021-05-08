package com.jinhyun.weggletoyproject.retrofit.response.validation.nickname

data class ValidationNicknameErrorResponse(
    val code: Int,
    val message: String,
    val method: String,
    val url: String
): ValidationNicknameResponse