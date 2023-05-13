package com.example.login_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edPassword: EditText
    private lateinit var edConfirm: EditText
    private lateinit var edAddress: EditText
    private lateinit var edMobile: EditText
    private lateinit var edCountry: EditText
    private lateinit var edPCode: EditText
    private lateinit var btn: Button
    private lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edUsername = findViewById(R.id.inputUsernameL)
        edPassword = findViewById(R.id.inputpassword)
        edConfirm = findViewById(R.id.inputConfirmPassword)
        btn = findViewById(R.id.btnRegister)
        tv = findViewById(R.id.alreadyHaveAnAccount)
        edAddress = findViewById(R.id.inputAddress)
        edMobile = findViewById(R.id.inputMobile)
        edCountry = findViewById(R.id.inputCountry)
        edPCode = findViewById(R.id.inputPostalCode)

        tv.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btn.setOnClickListener {
            val username = edUsername.text.toString()
            val password = edPassword.text.toString()
            val confirm = edConfirm.text.toString()
            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                if (password == confirm) {
                    if (!isValid(password)) {
                        Toast.makeText(applicationContext, "Password must contain at least 8 characters, having letters, digits, and special symbols", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Password and confirm password didn't match", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValid(password: String): Boolean {
        return password.length >= 8 &&
                password.matches("(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+".toRegex())
    }
}