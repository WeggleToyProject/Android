package com.jinhyun.weggletoyproject.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.jinhyun.weggletoyproject.R
import com.jinhyun.weggletoyproject.model.UserModel
import com.jinhyun.weggletoyproject.retrofit.methods.UserAPIMethods
import com.jinhyun.weggletoyproject.retrofit.request.SignUpRequest
import com.jinhyun.weggletoyproject.retrofit.request.UploadImageRequest
import com.jinhyun.weggletoyproject.retrofit.response.account.signup.SignUpErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.account.signup.SignUpOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.email.ValidationEmailErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.email.ValidationEmailOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.nickname.ValidationNicknameErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.nickname.ValidationNicknameOkResponse
import com.jinhyun.weggletoyproject.view.utils.FileUtils
import com.jinhyun.weggletoyproject.viewmodel.UserViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_register_profile.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.roundToInt

class RegisterProfileActivity : AppCompatActivity() {

    private var selectedProfileFile: File? = null

    private var passedNickname: String? = null
    private var passedEmail: String? = null

    companion object {
        private const val REQUEST_PICK_IMAGE = 1
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_profile)

        requestPermissions()
        setView()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 0
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty()) {
            for (idx in grantResults.indices) {
                if (grantResults[idx] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "부족한 권한이 있어 회원가입을 진행할 수 없습니다.",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    finish()
                    break
                }
            }
        } else {

            Toast.makeText(applicationContext, "부족한 권한이 있어 회원가입을 진행할 수 없습니다.", Toast.LENGTH_LONG).show()

            finish()
        }
    }

    private fun setView() {

        ll_btn_edit_image_register_profile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_PICK_IMAGE)
        }

        ViewCompat.setBackgroundTintList(
            btn_ok_register_profile, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )
        btn_ok_register_profile.setOnClickListener {

            if (validationIsEmpty().not()) {
                Toast.makeText(applicationContext, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(validationIsDupCheck().not()) {
                Toast.makeText(applicationContext, "중복확인이 필요합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showLoadingPanel()
            UserAPIMethods.signUpWithUploadImageProfile(
                makeUploadImageProfileRequest(),
                makeSignUpRequest()
            ) { response ->

                hideLoadingPanel()

                when (response) {
                    is SignUpOkResponse -> {
                        storeUserInfo(
                            response.signup_type,
                            response.social_id,
                            response.social_id, // replace push token temporary
                            response.os,
                            response.version_app,
                            response.email
                        )
                        goMainActivity()
                    }
                    is SignUpErrorResponse -> {
                        Toast.makeText(
                            applicationContext,
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            applicationContext,
                            "알 수 없는 에러가 발생하여 가입 여부 확인에 실패하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        ibtn_back_toolbar_register_profile.setOnClickListener {
            finish()
        }

        tv_dup_check_nickname_register_profile.setOnClickListener {

            val nickname: String = et_nickname_register_profile.text.toString()

            UserAPIMethods.validationUserNickname(nickname) { response ->
                when (response) {
                    is ValidationNicknameOkResponse -> {

                        passedNickname = nickname
                        Toast.makeText(applicationContext, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                    }
                    is ValidationNicknameErrorResponse -> {
                        Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(applicationContext, "중복확인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        tv_dup_check_email_register_profile.setOnClickListener {

            val email: String = et_email_register_profile.text.toString()

            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                UserAPIMethods.validationUserEmail(email) { response ->
                    when (response) {
                        is ValidationEmailOkResponse -> {

                            passedEmail = email
                            Toast.makeText(applicationContext, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show()
                        }
                        is ValidationEmailErrorResponse -> {
                            Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(applicationContext, "중복확인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(applicationContext, "이메일 형식을 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoadingPanel() {
        loading_panel_register_profile.visibility = View.VISIBLE
        cl_root_register_profile.isEnabled = false
    }

    private fun hideLoadingPanel() {
        loading_panel_register_profile.visibility = View.INVISIBLE
        cl_root_register_profile.isEnabled = true
    }

    private fun goMainActivity() {

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun makeUploadImageProfileRequest(): UploadImageRequest {
        return UploadImageRequest(selectedProfileFile!!)
    }

    private fun validationIsEmpty(): Boolean {
        return selectedProfileFile != null && et_nickname_register_profile.text.toString()
            .isNotEmpty() && et_description_register_profile.text.toString()
            .isNotEmpty() && et_email_register_profile.text.toString().isNotEmpty()
    }

    private fun validationIsDupCheck(): Boolean {

        val email: String = et_email_register_profile.text.toString()
        val nickname: String = et_nickname_register_profile.text.toString()

        return email == passedEmail && nickname == passedNickname
    }

    private fun makeSignUpRequest(): SignUpRequest {

        val signUpForm: Bundle = intent.getBundleExtra("signup_form")!!

        val signUpType: String = signUpForm.getString("signup_type")!!
        val socialId: String = signUpForm.getString("social_id")!!
        val interests: Int = signUpForm.getInt("interests")
        val age: Int = signUpForm.getInt("age")
        val gender: String = signUpForm.getString("gender")!!
        val pushToken: String = signUpForm.getString("push_token")!!
        val os: String = signUpForm.getString("os")!!
        val versionApp: String = signUpForm.getString("version_app")!!
        val nickname: String = et_nickname_register_profile.text.toString()
        val about: String = et_description_register_profile.text.toString()
        val email: String = et_email_register_profile.text.toString()

        return SignUpRequest(
            signUpType,
            socialId,
            email,
            nickname,
            about,
            interests,
            age,
            gender,
            pushToken,
            os,
            versionApp
        )
    }

    private fun storeUserInfo(
        signUpType: String,
        socialId: String,
        pushToken: String,
        os: String,
        versionApp: String,
        email: String
    ) {

        val userViewModel: UserViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(UserViewModel::class.java)

        userViewModel.insertUser(
            UserModel(
                null,
                signUpType,
                socialId,
                pushToken,
                os,
                versionApp,
                email
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                CropImage.activity(data.data)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this)
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                val resultUri = CropImage.getActivityResult(data).uri

                selectedProfileFile = getResizedImageFile(resultUri)

                val fileSizeKB : Long = selectedProfileFile!!.length() / 1024

                if (fileSizeKB > 1024) {
                    Toast.makeText(applicationContext, "이미지 크기는 1MB를 초과할 수 없습니다.",
                        Toast.LENGTH_SHORT).show()

                    selectedProfileFile = null
                } else {
                    civ_register_profile.load(resultUri)
                }
            }
        }
    }

    fun getResizedImageFile(data : Uri) : File {
        val openInputStream : InputStream = contentResolver.openInputStream(data)!!
        val bitmap : Bitmap = BitmapFactory.decodeStream(openInputStream)
        val resizedBitmap : Bitmap? = scaleDown(bitmap, 1024f, true)

        openInputStream.close()

        val resizedFile = File("${applicationContext.cacheDir}/tmp-weegle.jpg")

        resizedFile.createNewFile()
        val out : OutputStream = FileOutputStream(resizedFile)
        resizedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, out)

        return resizedFile
    }

    fun scaleDown(
        realImage : Bitmap,
        maxImageSize : Float,
        filter : Boolean
    ) : Bitmap? {
        val ratio = (maxImageSize / realImage.width)
            .coerceAtMost(maxImageSize / realImage.height)
        val width = (ratio * realImage.width).roundToInt()
        val height = (ratio * realImage.height).roundToInt()

        return Bitmap.createScaledBitmap(realImage, width, height, filter)
    }
}