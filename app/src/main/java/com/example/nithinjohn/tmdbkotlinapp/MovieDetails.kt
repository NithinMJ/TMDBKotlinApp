package com.example.nithinjohn.tmdbkotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MovieDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val movieTitle = intent.getStringExtra("movie_title")
        val movieBackdrop = intent.getStringExtra("movie_backdrop")
        val movieLanguage = intent.getStringExtra("movie_language")
        val movieRelease = intent.getStringExtra("movie_release")
        val movieOverview = intent.getStringExtra("movie_description")
        val moviePoster = intent.getStringExtra("movie_poster")
        val movieRating = intent.getStringExtra("movie_rating")


        title_detail.text = movieTitle
        language_detail.text = movieLanguage
        rating_detail.text = movieRating

        val mRelease = movieRelease
        val rel = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = rel.parse(mRelease)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val fmtOut = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val mReleaseDate = fmtOut.format(date)

        release_detail.text = mReleaseDate


        overview_detail.text = movieOverview

        Picasso.get()
                .load(PathFiles.IMAGE_URL_BACKDROP + movieBackdrop)
                .into(img_detail)

        Picasso.get()
                .load(PathFiles.IMAGE_URL_POSTER + moviePoster)
                .placeholder(R.drawable.loading)
                .into(poster_detail)

    }


}
