package com.jinhyun.weggletoyproject.retrofit.response.validation.email

data class ValidationEmailErrorResponse(
    val code: Int,
    val message: String,
    val method: String,
    val url: String
): ValidationEmailResponse