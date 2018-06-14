package com.example.nithinjohn.tmdbkotlinapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InitRetrofit {

    fun getInitRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(PathFiles.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getInitInstance(): ApiService {
        return getInitRetrofit().create(ApiService::class.java)
    }
}