package com.jinhyun.weggletoyproject.retrofit

import com.jinhyun.weggletoyproject.retrofit.service.WeggleToyService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        //WeggleToyProject Url로 바꿔주기
        private const val baseUrl = "http://15.165.194.188:3061"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weggleToy: WeggleToyService = retrofit.create(WeggleToyService::class.java)
    }
}