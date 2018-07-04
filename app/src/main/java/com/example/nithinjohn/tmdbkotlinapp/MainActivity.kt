package com.example.nithinjohn.tmdbkotlinapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    var visibleThreshold = 2
    var lastVisibleItem = 0
    var totalItemCount = 0

    private val mainActivityContract: MainActivityContract.Presenter = MainActivityPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fabric.with(this, Crashlytics())


        mainActivityContract.attachView(this)
        mainActivityContract.loadPage()

        fab.setOnClickListener {
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
                        mainActivityContract.loadPageMore()
                    }

                } else if (dy < 0) {
                    println("NO MORE PAGES")
                }


            }
        })
    }

    override fun showPage(moviesinformation: ArrayList<MovieList.MovieNames>) {
        val adapter = MainAdapter(this@MainActivity, moviesinformation)
        recyclerview_main.adapter = adapter
        recyclerview_main.layoutManager = GridLayoutManager(this@MainActivity, 2)
    }

    override fun showPageMore(moviesinformation: ArrayList<MovieList.MovieNames>, movieData: ArrayList<MovieList.MovieNames>) {
        moviesinformation.addAll(movieData)
        recyclerview_main.adapter.notifyDataSetChanged()
    }

    //Double tap on back button to exit
    override fun onBackPressed() {
        if (!mainActivityContract.handleBackPress()) {
            super.onBackPressed()
            //Exit Application on back press instead of moving to the first Activity
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            val backpressToast: Toast? = Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_LONG)
            backpressToast?.show()
        }
    }


}
