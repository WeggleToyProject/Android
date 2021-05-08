package com.jinhyun.weggletoyproject.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.jinhyun.weggletoyproject.R
import kotlinx.android.synthetic.main.activity_enter_personal_info.*

class EnterPersonalInfoActivity : AppCompatActivity(){

    private val TAG = "EnterPersonalInfoActivity"

    private val GENDER_MALE = "male"
    private val GENDER_FEMALE = "female"

    private var selectedGender: String = GENDER_FEMALE
    private var selectedAgeRange: Int = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_personal_info)

        setView()
    }

    private fun setView() {
        setTintToViews()
        setGenderButtons()
        setAgeRangeButtons()
        setBackButton()
        setOkButton()
    }

    private fun setTintToViews() {
        ViewCompat.setBackgroundTintList(
            btn_age_range_20_default, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )
        ViewCompat.setBackgroundTintList(
            btn_age_range_30_default, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )
        ViewCompat.setBackgroundTintList(
            btn_age_range_40_default, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )
        ViewCompat.setBackgroundTintList(
            btn_age_range_more50_default, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )


        ViewCompat.setBackgroundTintList(
            btn_age_range_20_selected, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )
        ViewCompat.setBackgroundTintList(
            btn_age_range_30_selected, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )
        ViewCompat.setBackgroundTintList(
            btn_age_range_40_selected, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )
        ViewCompat.setBackgroundTintList(
            btn_age_range_more50_selected, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )


        ViewCompat.setBackgroundTintList(
            btn_ok_enter_personal_info, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )
    }

    private fun setGenderButtons() {

        selectGender(selectedGender)

        ibtn_gender_man_default.setOnClickListener {
            selectGender(GENDER_MALE)
        }
        ibtn_gender_woman_default.setOnClickListener {
            selectGender(GENDER_FEMALE)
        }
    }

    private fun setAgeRangeButtons() {

        selectAgeRange(20)

        btn_age_range_20_default.setOnClickListener {
            selectAgeRange(20)
        }
        btn_age_range_30_default.setOnClickListener {
            selectAgeRange(30)
        }
        btn_age_range_40_default.setOnClickListener {
            selectAgeRange(40)
        }
        btn_age_range_more50_default.setOnClickListener {
            selectAgeRange(50)
        }
    }

    private fun setBackButton() {
        ibtn_toolbar_enter_personal_info.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("LongLogTag")
    private fun setOkButton() {

        btn_ok_enter_personal_info.setOnClickListener {

            val signUpForm: Bundle = intent.getParcelableExtra("signup_form")!!

            signUpForm.putString("gender", selectedGender)
            signUpForm.putInt("age", selectedAgeRange)

            val intent = Intent(applicationContext, RegisterProfileActivity::class.java)
            intent.putExtra("signup_form", signUpForm)
            startActivity(intent)
        }
    }

    private fun selectGender(gender: String) {

        selectedGender = gender

        when (gender) {
            GENDER_MALE -> {
                selectGenderMan()
            }
            GENDER_FEMALE -> {
                selectGenderWoman()
            }
        }
    }

    private fun selectGenderMan() {
        ibtn_gender_man_selected.visibility = View.VISIBLE
        ibtn_gender_woman_selected.visibility = View.INVISIBLE
    }

    private fun selectGenderWoman() {
        ibtn_gender_man_selected.visibility = View.INVISIBLE
        ibtn_gender_woman_selected.visibility = View.VISIBLE
    }

    private fun selectAgeRange(ageRange: Int) {

        selectedAgeRange = ageRange

        when (ageRange) {
            20 -> {
                selectAgeRange20()
            }
            30 -> {
                selectAgeRange30()
            }
            40 -> {
                selectAgeRange40()
            }
            50 -> {
                selectAgeRangeMore50()
            }
        }
    }

    private fun selectAgeRange20() {
        btn_age_range_20_selected.visibility = View.VISIBLE //
        btn_age_range_30_selected.visibility = View.INVISIBLE
        btn_age_range_40_selected.visibility = View.INVISIBLE
        btn_age_range_more50_selected.visibility = View.INVISIBLE

        btn_age_range_20_default.visibility = View.INVISIBLE //
        btn_age_range_30_default.visibility = View.VISIBLE
        btn_age_range_40_default.visibility = View.VISIBLE
        btn_age_range_more50_default.visibility = View.VISIBLE
    }

    private fun selectAgeRange30() {
        btn_age_range_20_selected.visibility = View.INVISIBLE
        btn_age_range_30_selected.visibility = View.VISIBLE //
        btn_age_range_40_selected.visibility = View.INVISIBLE
        btn_age_range_more50_selected.visibility = View.INVISIBLE

        btn_age_range_20_default.visibility = View.VISIBLE
        btn_age_range_30_default.visibility = View.INVISIBLE //
        btn_age_range_40_default.visibility = View.VISIBLE
        btn_age_range_more50_default.visibility = View.VISIBLE
    }

    private fun selectAgeRange40() {
        btn_age_range_20_selected.visibility = View.INVISIBLE
        btn_age_range_30_selected.visibility = View.INVISIBLE
        btn_age_range_40_selected.visibility = View.VISIBLE //
        btn_age_range_more50_selected.visibility = View.INVISIBLE

        btn_age_range_20_default.visibility = View.VISIBLE
        btn_age_range_30_default.visibility = View.VISIBLE
        btn_age_range_40_default.visibility = View.INVISIBLE //
        btn_age_range_more50_default.visibility = View.VISIBLE
    }

    private fun selectAgeRangeMore50() {
        btn_age_range_20_selected.visibility = View.INVISIBLE
        btn_age_range_30_selected.visibility = View.INVISIBLE
        btn_age_range_40_selected.visibility = View.INVISIBLE
        btn_age_range_more50_selected.visibility = View.VISIBLE //

        btn_age_range_20_default.visibility = View.VISIBLE
        btn_age_range_30_default.visibility = View.VISIBLE
        btn_age_range_40_default.visibility = View.VISIBLE
        btn_age_range_more50_default.visibility = View.INVISIBLE //
    }
}