package com.example.nithinjohn.tmdbkotlinapp

interface MovieDetailsContract {
    interface View

    interface Presenter {

        fun attachView(view: MovieDetailsContract.View)

        fun detach()

        fun loadDate(releaseDate: String):String
    }
}