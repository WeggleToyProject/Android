package com.jinhyun.weggletoyproject.retrofit.service

import com.jinhyun.weggletoyproject.retrofit.response.account.signup.SignUpOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.signupcheck.ExistUserResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.uploadprofile.UploadImageOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.edit.myuserinfo.EditMyUserInfoOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my.MyUserInfoOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.email.ValidationEmailOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.nickname.ValidationNicknameOkResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface WeggleToyService {

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/api/public/user/signup")
    fun signUp(@Body params: HashMap<String, Any>): Call<SignUpOkResponse>

    @GET("/api/public/user/signup/check")
    fun signUpCheck(
        @Query("signup_type") signUpType: String,
        @Query("social_id") socialId: String,
        @Query("push_token") pushToken: String,
        @Query("os") os: String,
        @Query("version_app") versionApp: String,
        @Query("email") email : String
    ): Call<ExistUserResponse>

    @GET("/api/public/user/email/check")
    fun validationUserEmail(
        @Query("email") email: String
    ): Call<ValidationEmailOkResponse>

    @GET("/api/public/user/nickname/check")
    fun validationUserNickname(
        @Query("nickname") nickname: String
    ): Call<ValidationNicknameOkResponse>

    @GET("/api/private/user/info/me")
    fun myUserInfo(@Header("access_token") accessToken: String): Call<MyUserInfoOkResponse>

    @Headers("accept: application/json", "content-type: application/json")
    @PUT("/api/private/user")
    fun editMyUserInfo(
        @Header("access_token") accessToken: String,
        @Body params: HashMap<String, Any>
    ): Call<EditMyUserInfoOkResponse>

    @Multipart
    @POST("/api/public/file")
    fun uploadImageProfile(
        @Part file: MultipartBody.Part
    ): Call<UploadImageOkResponse>
}