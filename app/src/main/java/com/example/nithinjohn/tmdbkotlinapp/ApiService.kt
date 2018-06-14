package com.example.nithinjohn.tmdbkotlinapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("popular")
    fun request_popular(
            @Query("api_key") apiKey: String,
            @Query("page") pageNumber: Int
    ): Call<MovieList>

}