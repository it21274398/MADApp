package com.example.mycrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button


class MainActivity : AppCompatActivity() {
    private lateinit var btnFetchData: Button


    //button to go to edit profile page
    fun Editprofile(view : View){
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnFetchData = findViewById(R.id.btnFetchData)

        // Fetching data from the server
        btnFetchData.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }



    }
}