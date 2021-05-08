package com.jinhyun.weggletoyproject.retrofit.methods

import com.jinhyun.weggletoyproject.retrofit.RetrofitService
import com.jinhyun.weggletoyproject.retrofit.request.EditMyUserInfoRequest
import com.jinhyun.weggletoyproject.retrofit.request.SignUpCheckRequest
import com.jinhyun.weggletoyproject.retrofit.request.SignUpRequest
import com.jinhyun.weggletoyproject.retrofit.request.UploadImageRequest
import com.jinhyun.weggletoyproject.retrofit.response.account.signup.SignUpErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.signup.SignUpOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.signup.SignUpResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.signupcheck.ExistUserResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.signupcheck.NewUserOrErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.signupcheck.SignUpCheckResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.uploadprofile.UploadImageErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.uploadprofile.UploadImageOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.uploadprofile.UploadImageResponse
import com.jinhyun.weggletoyproject.retrofit.response.edit.myuserinfo.EditMyUserInfoErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.edit.myuserinfo.EditMyUserInfoOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.edit.myuserinfo.EditMyUserInfoResponse
import com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my.MyUserInfoErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my.MyUserInfoOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my.MyUserInfoResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.email.ValidationEmailErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.email.ValidationEmailOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.email.ValidationEmailResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.nickname.ValidationNicknameErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.nickname.ValidationNicknameOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.nickname.ValidationNicknameResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UserAPIMethods {

    companion object {
        fun validationUserNickname(
            nickname: String,
            callback: (ValidationNicknameResponse?) -> Unit
        ) {
            RetrofitService.weggleToy.validationUserNickname(nickname)
                .enqueue(
                    MethodCallback.generalCallback<ValidationNicknameResponse, ValidationNicknameOkResponse, ValidationNicknameErrorResponse>(
                        callback
                    )
                )
        }

        fun validationUserEmail(
            email: String,
            callback: (ValidationEmailResponse?) -> Unit
        ) {
            RetrofitService.weggleToy.validationUserEmail(email)
                .enqueue(
                    MethodCallback.generalCallback<ValidationEmailResponse, ValidationEmailOkResponse, ValidationEmailErrorResponse>(
                        callback
                    )
                )
        }

        fun editMyUserInfo(
            accessToken: String,
            uploadImageProfileRequest: UploadImageRequest? = null,
            editMyUserInfoRequest: EditMyUserInfoRequest,
            callback: (response: EditMyUserInfoResponse?) -> Unit
        ) {

            if (uploadImageProfileRequest == null) {

                executeEditUserInfo("", accessToken, editMyUserInfoRequest, callback)

            } else {

                uploadImageProfile(uploadImageProfileRequest.profileImageFile) { response ->

                    if (response is UploadImageOkResponse) {

                        executeEditUserInfo(
                            response.filename,
                            accessToken,
                            editMyUserInfoRequest,
                            callback
                        )
                    }
                }
            }
        }

        private fun executeEditUserInfo(
            imageFileName: String,
            accessToken: String,
            editMyUserInfoRequest: EditMyUserInfoRequest,
            callback: (response: EditMyUserInfoResponse?) -> Unit
        ) {

            editMyUserInfoRequest.filename = imageFileName

            RetrofitService.weggleToy.editMyUserInfo(accessToken, editMyUserInfoRequest.toHashMap())
                .enqueue(
                    MethodCallback.generalCallback<EditMyUserInfoResponse, EditMyUserInfoOkResponse, EditMyUserInfoErrorResponse>(
                        callback
                    )
                )
        }

        fun signUpCheck(
            signUpCheckRequest: SignUpCheckRequest,
            callback: (accessToken: SignUpCheckResponse?) -> Unit
        ) {

            RetrofitService.weggleToy.signUpCheck(
                signUpCheckRequest.signUpType,
                signUpCheckRequest.socialId,
                signUpCheckRequest.pushToken,
                signUpCheckRequest.os,
                signUpCheckRequest.versionApp,
                signUpCheckRequest.email
            ).enqueue(
                MethodCallback.generalCallback<SignUpCheckResponse, ExistUserResponse, NewUserOrErrorResponse>(
                    callback
                )
            )
        }

        fun signUpWithUploadImageProfile(
            uploadImageRequest: UploadImageRequest,
            signUpRequest: SignUpRequest,
            callback: (response: SignUpResponse?) -> Unit
        ) {

            uploadImageProfile(uploadImageRequest.profileImageFile) { response ->

                if (response is UploadImageOkResponse) {

                    executeSignUp(response.filename, signUpRequest, callback)
                }
            }
        }

        private fun uploadImageProfile(
            profileImage: File,
            callback: (response: UploadImageResponse?) -> Unit
        ) {

            RetrofitService.weggleToy.uploadImageProfile(getMultiPartFile(profileImage)).enqueue(
                MethodCallback.generalCallback<UploadImageResponse, UploadImageOkResponse, UploadImageErrorResponse>(
                    callback
                )
            )
        }

        private fun getMultiPartFile(file: File): MultipartBody.Part {
            return MultipartBody.Part.createFormData(
                "file",
                file.name,
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            )
        }

        private fun executeSignUp(
            imageFileName: String,
            signUpRequest: SignUpRequest,
            callback: (response: SignUpResponse?) -> Unit
        ) {

            signUpRequest.filename = imageFileName

            RetrofitService.weggleToy.signUp(signUpRequest.toHashMap())
                .enqueue(
                    MethodCallback.generalCallback<SignUpResponse, SignUpOkResponse, SignUpErrorResponse>(
                        callback
                    )
                )
        }

        fun myUserInfo(
            accessToken: String,
            callback: (response: MyUserInfoResponse?) -> Unit
        ) {
            RetrofitService.weggleToy.myUserInfo(accessToken)
                .enqueue(
                    MethodCallback.generalCallback<MyUserInfoResponse, MyUserInfoOkResponse, MyUserInfoErrorResponse>(
                        callback
                    )
                )
        }
    }
}