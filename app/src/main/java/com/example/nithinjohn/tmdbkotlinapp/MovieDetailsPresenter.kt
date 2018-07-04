package com.example.nithinjohn.tmdbkotlinapp

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailsPresenter : MovieDetailsContract.Presenter {

    private var view: MovieDetailsContract.View? = null

    override fun attachView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
    }

    override fun loadDate(releaseDate: String):String {
        val mRelease = releaseDate
        val rel = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = rel.parse(mRelease)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val fmtOut = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        return fmtOut.format(date)
    }


}