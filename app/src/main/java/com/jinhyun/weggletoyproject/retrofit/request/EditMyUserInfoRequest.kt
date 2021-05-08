package com.jinhyun.weggletoyproject.retrofit.request

class EditMyUserInfoRequest(
    val nickname: String,
    val about: String,
    val email: String,
    val interests: Int,
    val age: Int,
    val gender: String,
    var filename: String = ""
) {
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "nickname" to nickname,
            "about" to about,
            "email" to email,
            "interests" to interests,
            "age" to age,
            "gender" to gender,
            "filename" to filename
        )
    }
}