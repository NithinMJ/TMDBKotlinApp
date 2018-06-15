package com.example.nithinjohn.tmdbkotlinapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    var pageNumber: Int = 1
    internal var lastPress: Long = 0
    var moviesinformation = ArrayList<MovieList.MovieNames>()
    var loading = true
    var visibleThreshold = 2
    var lastVisibleItem = 0
    var totalItemCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPopular()

        fab.setOnClickListener { v: View? ->
            recyclerview_main.scrollToPosition(0)
            fab.hide()
        }

        recyclerview_main.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {

                    fab.show()
                    totalItemCount = (recyclerview_main.layoutManager as LinearLayoutManager).itemCount
                    lastVisibleItem = (recyclerview_main.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                    if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        pageNumber++
                        loadMore()
                    }

                } else if (dy < 0) {
                    println("NO MORE PAGES")
                }


            }
        })

    }

    //Double tap on back button to exit
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastPress > 5000) {
            val backpressToast: Toast? = Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_LONG)
            backpressToast?.show()
            lastPress = currentTime

        } else {
            super.onBackPressed()
            //Exit Application on back press instead of moving to the first Activity
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun getPopular() {
        val api = InitRetrofit().getInitInstance()
        val call = api.request_popular(PathFiles.API_KEY, pageNumber)
        call.enqueue(object : Callback<MovieList> {

            override fun onFailure(call: Call<MovieList>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<MovieList>?, response: retrofit2.Response<MovieList>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        moviesinformation = response.body()?.data!!
                        val adapter = MainAdapter(this@MainActivity, moviesinformation)
                        recyclerview_main.adapter = adapter
                        recyclerview_main.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    }
                }
            }

        })
    }

    private fun loadMore() {
        val api = InitRetrofit().getInitInstance()
        val call = api.request_popular(PathFiles.API_KEY, pageNumber)
        call.enqueue(object : Callback<MovieList> {

            override fun onFailure(call: Call<MovieList>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<MovieList>?, response: retrofit2.Response<MovieList>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        val d1 = response.body()?.data!!
                        moviesinformation.addAll(d1)
                        recyclerview_main.adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }


}
