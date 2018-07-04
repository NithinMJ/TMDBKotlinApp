package com.example.nithinjohn.tmdbkotlinapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.Window
import android.view.WindowManager
import android.widget.Toast

class SplashActivity : Activity(){
    internal var check = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_loader_screen)

        loadScreen()
    }

    private fun loadScreen(){
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
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        check = false
                    }
                }
                if (check) {
                    handler.postDelayed(this, 3000)
                }

            }

        }, 2000)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    //Handling onRestart
    override fun onRestart() {
        loadScreen()
        super.onRestart()

    }

    //Handling onResume
    override fun onResume() {
        loadScreen()
        super.onResume()
    }


}
