package com.jinhyun.weggletoyproject.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.jinhyun.weggletoyproject.R
import com.jinhyun.weggletoyproject.view.adapter.InterestGridAdapter
import com.jinhyun.weggletoyproject.view.adapter.item.InterestItem
import com.jinhyun.weggletoyproject.view.adapter.itemdecoration.InterestItemDecoration
import kotlinx.android.synthetic.main.activity_interest_selection.*

class InterestSelectionActivity : AppCompatActivity() {

    private lateinit var interestGridAdapter: InterestGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest_selection)

        setView()
    }

    private fun setView() {

        setInterestSelectionGrid()
        initOkButton()
        initBackButton()
    }

    private fun setInterestSelectionGrid() {

        interestGridAdapter = InterestGridAdapter().apply {
            itemClickListener = object : InterestGridAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    select(position)
                }
            }
        }

        val interestList: ArrayList<InterestItem> = ArrayList()

        interestList.add(InterestItem(1, R.drawable.interest1, "먹거리", false))
        interestList.add(InterestItem(2, R.drawable.interest2, "음료", false))
        interestList.add(InterestItem(4, R.drawable.interest3, "인테리어\n소품", false))
        interestList.add(InterestItem(8, R.drawable.interest4, "악세사리", false))
        interestList.add(InterestItem(16, R.drawable.interest5, "휴대폰\n주변 기기", false))
        interestList.add(InterestItem(32, R.drawable.interest6, "비누/캔들", false))
        interestList.add(InterestItem(64, R.drawable.interest7, "가죽 공예", false))
        interestList.add(InterestItem(128, R.drawable.interest8, "꽃", false))
        interestList.add(InterestItem(256, R.drawable.interest9, "반려견", false))

        interestGridAdapter.setItems(interestList)

        rv_interest_selection.run {
            setHasFixedSize(true)
            adapter = interestGridAdapter
        }

        rv_interest_selection.addItemDecoration(InterestItemDecoration(20))
    }

    private fun initOkButton() {

        ViewCompat.setBackgroundTintList(
            btn_ok_interest_selection, ContextCompat.getColorStateList(
                applicationContext,
                R.color.weggle_purple
            )
        )

        btn_ok_interest_selection.setOnClickListener {

            val signUpForm: Bundle = intent.getBundleExtra("signup_form")!!
            var interests = 0

            interestGridAdapter.getSelectedItems().forEach {
                interests += it.itemCode
            }

            signUpForm.putInt("interests", interests)

            val intent = Intent(applicationContext, EnterPersonalInfoActivity::class.java)
            intent.putExtra("signup_form", signUpForm)
            startActivity(intent)
        }
    }

    private fun initBackButton() {
        ibtn_toolbar_interest_selection.setOnClickListener {
            finish()
        }
    }
}