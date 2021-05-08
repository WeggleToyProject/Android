package com.jinhyun.weggletoyproject.retrofit.response.validation.email

data class ValidationEmailOkResponse(
    val success: String,
    val method: String,
    val url: String
): ValidationEmailResponse