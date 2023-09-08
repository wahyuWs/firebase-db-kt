package com.wahyus.firebasedbkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wahyus.firebasedbkt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.btnInsert.setOnClickListener {
            startActivity(Intent(this, InsertActivity2::class.java))
        }
        activityMainBinding.btnFetchData.setOnClickListener {
            startActivity(Intent(this, FetchActivity::class.java))
        }
    }
}