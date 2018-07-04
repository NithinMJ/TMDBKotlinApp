package com.example.nithinjohn.tmdbkotlinapp

interface MainActivityContract {
    interface View {
        fun showPage(moviesinformation: ArrayList<MovieList.MovieNames>)

        fun showPageMore(moviesinformation: ArrayList<MovieList.MovieNames>, movieData: ArrayList<MovieList.MovieNames>)
    }

    interface Presenter {

        fun attachView(view: MainActivityContract.View)

        fun detachView()

        fun loadPage()

        fun loadPageMore()

        fun handleBackPress(): Boolean

    }
}