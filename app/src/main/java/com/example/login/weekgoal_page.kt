package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class weekgoal_page: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weekgoal_page)

        val btnIniciar = findViewById<Button>(R.id.btnMapa)
        btnIniciar.setOnClickListener {
            val intent = Intent(this, mapa_page::class.java)
            startActivity(intent)
        }
        val btnIniciar2 = findViewById<Button>(R.id.btnMapa2)
        btnIniciar2.setOnClickListener {
            val intent = Intent(this, mapa_page::class.java)
            startActivity(intent)
        }

        val btnIniciar3 = findViewById<Button>(R.id.btnMapa3)
        btnIniciar3.setOnClickListener {
            val intent = Intent(this, mapa_page::class.java)
            startActivity(intent)
        }
    }
}