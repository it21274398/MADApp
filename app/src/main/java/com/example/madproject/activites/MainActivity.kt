package com.example.madproject.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.madproject.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    fun gotToTextileActivity(view: View) {
        val intent = Intent(this, itemCategory1::class.java)
        startActivity(intent)
    }
    fun gotoJewelleryActivity(view: View) {
        val intent = Intent(this, itemCategory2::class.java)
        startActivity(intent)
    }fun gotoKitchendiningActivity(view: View) {
        val intent = Intent(this, itemCategory3::class.java)
        startActivity(intent)
    }
    fun gotoWoodenActivity(view: View) {
        val intent = Intent(this, itemCategory4::class.java)
        startActivity(intent)
    }
    fun gotoRushreedActivity(view: View) {
        val intent = Intent(this, itemCategory5::class.java)
        startActivity(intent)
    }

    fun goAdminAddActivity(view: View) {
        val intent = Intent(this, AdminPanel::class.java)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
        setContentView(R.layout.activity_main)


    }
}