package com.example.nithinjohn.tmdbkotlinapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_loader_screen.*

class LoaderScreen : Activity() {

    internal var check = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_loader_screen)

        loadPage()
    }

    protected fun loadPage() {
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (!isNetworkAvailable()) {
                    runOnUiThread {
                        Toast.makeText(baseContext, "No Internet Connectivity. Please make sure that you are connected to the internet",
                                Toast.LENGTH_SHORT).show()
                    }

                } else {
                    runOnUiThread {
                        val `in` = Intent(this@LoaderScreen, MainActivity::class.java)
                        startActivity(`in`)
                        check = false
                    }
                }
                if (check) {
                    handler.postDelayed(this, 3000)
                }

            }

        }, 2000)
    }

    //Handling onRestart
    override fun onRestart() {
        loadPage()
        super.onRestart()

    }

    //Handling onResume
    override fun onResume() {
        loadPage()
        super.onResume()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}
