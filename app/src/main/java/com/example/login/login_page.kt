package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class login_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        val btnIrAPestana = findViewById<Button>(R.id.btnI)
        val emailEditText = findViewById<EditText>(R.id.e_mail)
        val passwordEditText = findViewById<EditText>(R.id.password)

        btnIrAPestana.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email == "hdperez@espe.edu.ec" && password == "espe") {
                val intent = Intent(this, menu_page::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

