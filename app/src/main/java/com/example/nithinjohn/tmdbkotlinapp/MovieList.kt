package com.example.nithinjohn.tmdbkotlinapp

import com.google.gson.annotations.SerializedName

class MovieList {

    @SerializedName("results")
    var data: ArrayList<MovieNames>? = null

    inner class MovieNames {

        @SerializedName("title")
        val movieTitle: String? = null

        @SerializedName("poster_path")
        val poster_path: String? = null

        @SerializedName("release_date")
        val release_date: String? = null

        @SerializedName("vote_average")
        val movie_rating: String? = null

        @SerializedName("backdrop_path")
        val backdrop_path: String? = null

        @SerializedName("original_language")
        val original_language: String? = null

        @SerializedName("overview")
        val overview: String? = null

    }
}