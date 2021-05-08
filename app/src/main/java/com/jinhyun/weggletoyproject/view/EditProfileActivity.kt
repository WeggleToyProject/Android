package com.jinhyun.weggletoyproject.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import coil.api.load
import com.jinhyun.weggletoyproject.R
import com.jinhyun.weggletoyproject.constants.Constants
import com.jinhyun.weggletoyproject.retrofit.methods.UserAPIMethods
import com.jinhyun.weggletoyproject.retrofit.request.EditMyUserInfoRequest
import com.jinhyun.weggletoyproject.retrofit.request.UploadImageRequest
import com.jinhyun.weggletoyproject.retrofit.response.edit.myuserinfo.EditMyUserInfoErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.edit.myuserinfo.EditMyUserInfoOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my.MyUserInfoItem
import com.jinhyun.weggletoyproject.retrofit.response.myuserinfo.my.MyUserInfoOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.email.ValidationEmailErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.email.ValidationEmailOkResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.nickname.ValidationNicknameErrorResponse
import com.jinhyun.weggletoyproject.retrofit.response.validation.nickname.ValidationNicknameOkResponse
import com.jinhyun.weggletoyproject.utils.AccessTokenManager
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.roundToInt

class EditProfileActivity : AppCompatActivity() {
    private var myUserInfo: MyUserInfoItem? = null
    private var selectedProfileFile: File? = null
    private var passedNickname: String? = null
    private var passedEmail: String? = null

    private lateinit var oldNickname: String
    private lateinit var oldEmail: String

    companion object {
        private const val REQUEST_PICK_IMAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        showLoadingPanel()

        UserAPIMethods.myUserInfo(AccessTokenManager(applicationContext).getToken()!!) { response ->

            if (response is MyUserInfoOkResponse) {

                myUserInfo = response.item
            }

            setView()
            mapInfoToViews()

            hideLoadingPanel()
        }
    }

    private fun setView() {
        ll_btn_edit_image_edit_profile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_PICK_IMAGE)
        }

        ViewCompat.setBackgroundTintList(
            btn_ok_edit_profile, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )

        btn_ok_edit_profile.setOnClickListener {

            if (validation().not()) {
                Toast.makeText(applicationContext, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (validationIsDupCheck().not()) {
                Toast.makeText(applicationContext, "중복확인이 필요합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            requestPermissions()
        }

        ibtn_back_toolbar_toolbar_edit_profile.setOnClickListener {
            finish()
        }


        tv_dup_check_nickname_edit_profile.setOnClickListener {

            val nickname: String = et_nickname_edit_profile.text.toString()

            if (nickname == oldNickname) {
                Toast.makeText(applicationContext, "현재 닉네임입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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

        tv_dup_check_email_edit_profile.setOnClickListener {

            val email: String = et_email_edit_profile.text.toString()

            if (email == oldEmail) {
                Toast.makeText(applicationContext, "현재 이메일입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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
        }
    }

    private fun mapInfoToViews() {
        val info: MyUserInfoItem = myUserInfo!!

        val profileImageUrl = "${Constants.contentsDomain}/${info.filename}"

        civ_edit_profile.load(profileImageUrl)
        et_nickname_edit_profile.setText(info.nickname)
        et_description_edit_profile.setText(info.about)
        et_email_edit_profile.setText(info.email)

        oldNickname = info.nickname
        oldEmail = info.email
    }

    private fun validationIsDupCheck(): Boolean {

        val email: String = et_email_edit_profile.text.toString()
        val nickname: String = et_nickname_edit_profile.text.toString()

        val validationEmail: Boolean = email == passedEmail || email == oldEmail
        val validationNickname: Boolean = nickname == passedNickname || nickname == oldNickname

        return validationEmail && validationNickname
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

    private fun executeEditInfo() {

        showLoadingPanel()
        UserAPIMethods.editMyUserInfo(
            AccessTokenManager(applicationContext).getToken()!!,
            makeImageUploadRequest(),
            makeEditMyUserInfoRequest()
        ) { response ->

            when (response) {
                is EditMyUserInfoOkResponse -> {

                    val newProfile = response.item

                    val intent = Intent()
                    intent.putExtra("image_profile", newProfile.filename)
                    intent.putExtra("nickname", newProfile.nickname)
                    intent.putExtra("about", newProfile.about)
                    setResult(0, intent)

                    Toast.makeText(applicationContext, "프로필 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                    finish()
                }
                is EditMyUserInfoErrorResponse -> {
                    hideLoadingPanel()
                    Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    hideLoadingPanel()
                    Toast.makeText(applicationContext, "프로필 수정에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty()) {

            var okFlag = true
            for (idx in grantResults.indices) {
                if (grantResults[idx] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        applicationContext,
                        "부족한 권한이 있어 프로필 수정을 진행할 수 없습니다.",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    okFlag = false
                    break
                }
            }

            if (okFlag) {
                executeEditInfo()
            }

        } else {

            Toast.makeText(applicationContext, "부족한 권한이 있어 프로필 수정을 진행할 수 없습니다.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun validation(): Boolean {
        return et_nickname_edit_profile.text.isNotEmpty() && et_description_edit_profile.text.isNotEmpty() && et_email_edit_profile.text.isNotEmpty()
    }

    fun isEditedProfile(): Boolean {
        return selectedProfileFile != null
    }

    fun makeImageUploadRequest(): UploadImageRequest? {

        if (isEditedProfile()) {
            return UploadImageRequest(selectedProfileFile!!)
        } else {
            return null
        }
    }

    fun makeEditMyUserInfoRequest(): EditMyUserInfoRequest {

        val info: MyUserInfoItem = myUserInfo!!

        val nickname: String = et_nickname_edit_profile.text.toString()
        val about: String = et_description_edit_profile.text.toString()
        val email: String = et_email_edit_profile.text.toString()
        val interests: Int = info.interests
        val age: Int = info.age
        val gender: String = info.gender

        return EditMyUserInfoRequest(
            nickname,
            about,
            email,
            interests,
            age,
            gender
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_PICK_IMAGE) {
            if (data != null) {

                selectedProfileFile =
                    getResizedImageFile(data.data!!)
                val fileSizeKB: Long = selectedProfileFile!!.length() / 1024

                if (fileSizeKB > 1024) {
                    Toast.makeText(applicationContext, "이미지 크기는 1MB를 초과할 수 없습니다.",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    selectedProfileFile = null
                } else {
                    civ_edit_profile.load(data.data!!)
                }
            }
        }
    }

    fun getResizedImageFile(data: Uri): File {

        val openInputStream: InputStream = contentResolver.openInputStream(data)!!
        val bitmap: Bitmap = BitmapFactory.decodeStream(openInputStream)
        val resizedBitmap: Bitmap? = scaleDown(bitmap, 1024f, true)

        openInputStream.close()

        Log.d("tag", "${applicationContext.cacheDir}/tmp-weggle.jpg")
        val resizedFile = File("${applicationContext.cacheDir}/tmp-weggle.jpg")

        resizedFile.createNewFile()
        val out: OutputStream = FileOutputStream(resizedFile)
        resizedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, out)

        return resizedFile
    }

    fun scaleDown(
        realImage: Bitmap,
        maxImageSize: Float,
        filter: Boolean
    ): Bitmap? {
        val ratio = (maxImageSize / realImage.width).coerceAtMost(maxImageSize / realImage.height)
        val width = (ratio * realImage.width).roundToInt()
        val height = (ratio * realImage.height).roundToInt()
        return Bitmap.createScaledBitmap(
            realImage, width,
            height, filter
        )
    }

    private fun showLoadingPanel() {

        loading_panel_edit_profile.visibility = View.VISIBLE
    }

    private fun hideLoadingPanel() {

        loading_panel_edit_profile.visibility = View.INVISIBLE
    }
}