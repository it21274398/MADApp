package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1 = findViewById<Button>(R.id.addp)
        btn2 = findViewById<Button>(R.id.editp)
        btn3 = findViewById<Button>(R.id.readp)

        btn1.setOnClickListener{
            val intent = Intent(this, Create_Payment::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener{
            val intent = Intent(this, Edit_Payment::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener{
            val intent = Intent(this, Show_Payment::class.java)
            startActivity(intent)
        }
    }
}
