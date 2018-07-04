package com.example.nithinjohn.tmdbkotlinapp

import retrofit2.Call
import retrofit2.Callback

class MainActivityPresenter : MainActivityContract.Presenter {
    private var view: MainActivityContract.View? = null

    private var pageNumber: Int = 1
    var moviesinformation = ArrayList<MovieList.MovieNames>()
    private var lastPress: Long = 0

    override fun attachView(view: MainActivityContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadPage() {
        val api = InitRetrofit().getInitInstance()
        val call = api.request_popular(PathFiles.API_KEY, pageNumber)
        call.enqueue(object : Callback<MovieList> {

            override fun onFailure(call: Call<MovieList>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<MovieList>?, response: retrofit2.Response<MovieList>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        moviesinformation = response.body()?.data!!
                        view?.showPage(moviesinformation)
                    }
                }
            }

        })
    }

    override fun loadPageMore() {
        val api = InitRetrofit().getInitInstance()
        val call = api.request_popular(PathFiles.API_KEY, pageNumber++)
        call.enqueue(object : Callback<MovieList> {

            override fun onFailure(call: Call<MovieList>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<MovieList>?, response: retrofit2.Response<MovieList>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        val movieData = response.body()?.data!!
                        view?.showPageMore(moviesinformation, movieData)
//
                    }
                }
            }

        })
    }

    override fun handleBackPress(): Boolean {
        val currentTime = System.currentTimeMillis()
        return if (currentTime - lastPress > 5000) {
            lastPress = currentTime
            true
        } else {
            false
        }

    }
}