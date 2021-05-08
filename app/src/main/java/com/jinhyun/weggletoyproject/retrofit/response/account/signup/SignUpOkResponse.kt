package com.jinhyun.weggletoyproject.retrofit.response.account.signup

data class SignUpOkResponse(
    val uid: Int,
    val is_deleted: Int,
    val created_time: String,
    val updated_time: String,
    val signup_type: String,
    val social_id: String,
    val nickname: String,
    val about: String,
    val gender: String,
    val interests: Int,
    val age: Int,
    val latitude: Double,
    val longitude: Double,
    val filename: String,
    val access_token: String,
    val os: String,
    val version_app: String,
    val email: String
): SignUpResponse