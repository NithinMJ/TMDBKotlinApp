package com.example.nithinjohn.tmdbkotlinapp

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MainAdapter(context: Context, movie: ArrayList<MovieList.MovieNames>?) : RecyclerView.Adapter<MainAdapter.CustomViewHolder>(),MovieDetailsContract.View{

    var mMovieNames: List<MovieList.MovieNames>? = movie
    var mContext: Context? = context
    private val movieDetailsContract: MovieDetailsContract.Presenter = MovieDetailsPresenter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val inflater = LayoutInflater.from(mContext).inflate(R.layout.movie_info, parent, false)

        return CustomViewHolder(inflater)

    }

    override fun getItemCount(): Int = mMovieNames!!.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val MovieData: MovieList.MovieNames = mMovieNames!!.get(position)

        Picasso.get()
                .load(PathFiles.IMAGE_URL_BACKDROP + MovieData.poster_path)
                .placeholder(R.drawable.loading)
                .into(holder.img_View)


        holder.cardView.setOnClickListener {

            val intent = Intent(mContext?.applicationContext, MovieDetails::class.java)
            intent.putExtra("movie_backdrop", MovieData.backdrop_path)
            intent.putExtra("movie_title", MovieData.movieTitle)
            intent.putExtra("movie_release", MovieData.release_date)
            intent.putExtra("movie_language", MovieData.original_language)
            intent.putExtra("movie_description", MovieData.overview)
            intent.putExtra("movie_poster", MovieData.poster_path)
            intent.putExtra("movie_rating", MovieData.movie_rating)
            mContext!!.startActivity(intent)
        }


        movieDetailsContract.attachView(this)
        holder.releaseDate.text = movieDetailsContract.loadDate(MovieData.release_date.toString())


        holder.movieTitle.text = MovieData.movieTitle
        holder.movieRate.text = MovieData.movie_rating

    }

    class CustomViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var img_View = itemView?.findViewById<View>(R.id.img_view) as ImageView
        var movieTitle = itemView?.findViewById<View>(R.id.movie_name) as TextView
        var releaseDate = itemView?.findViewById<View>(R.id.release_date) as TextView
        var movieRate = itemView?.findViewById<View>(R.id.rating) as TextView
        var cardView = itemView?.findViewById<View>(R.id.card_view) as CardView

    }

}





