package com.example.madproject.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.madproject.R

class itemCategory1 : AppCompatActivity() {

    fun gotofetchingdata(view: View) {
        val intent = Intent(this, FetchingActivity ::class.java)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_category1)
    }
}