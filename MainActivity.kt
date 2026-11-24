package com.example.banco_movil

import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Obtenemos la vista de la imagen
        val imgRobot = findViewById<ImageView>(R.id.imgRobot)

        // 2. Estado inicial: desplazada 1000px hacia abajo y transparente
        imgRobot.translationY = 1000f
        imgRobot.alpha = 0f

        // 3. Animación: sube a su posición natural volviéndose visible
        imgRobot.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(1200) // 1.2 segundos
            .setInterpolator(android.view.animation.OvershootInterpolator(1.2F)) // Movimiento suave al frenar
            .start()
    }
}