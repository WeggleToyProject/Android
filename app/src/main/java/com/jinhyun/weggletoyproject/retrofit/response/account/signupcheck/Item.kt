package com.jinhyun.weggletoyproject.retrofit.response.account.signupcheck

data class Item(
    val uid: Int,
    val is_deleted: Int,
    val created_time: String,
    val updated_time: String,
    val signup_type: String,
    val social_id: String,
    val email: String,
    val pwd: String,
    val nickname: String,
    val about: String,
    val gender: String,
    val interests: Int,
    val age: Int,
    val latitude: Double,
    val longitude: Double,
    val access_token: String,
    val os: String,
    val version_app: String
)