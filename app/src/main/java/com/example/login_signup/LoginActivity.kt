package com.example.login_signup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login_signup.R.*

class LoginActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edPassword: EditText
    private lateinit var btn: Button
    private lateinit var tv: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_login)

        edUsername = findViewById(id.inputUsernameL)
        edPassword = findViewById(id.inputPssword)
        btn = findViewById(id.btnLogin)
        tv = findViewById(id.notHave)

        btn.setOnClickListener {
            val username = edUsername.text.toString()
            val password = edPassword.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            }
        }

        tv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
