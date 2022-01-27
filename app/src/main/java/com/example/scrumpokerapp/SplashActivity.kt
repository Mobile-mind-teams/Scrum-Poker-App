package com.example.scrumpokerapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.scrumpokerapp.view.activity.LoginSignupActivity
import com.example.scrumpokerapp.view.activity.MainActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, LoginSignupActivity::class.java))
            finish()
        },SPLASH_TIME_OUT)
    }
}
