package com.example.bancomovil

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tvTitulo = findViewById<TextView>(R.id.tvTitulo)
        tvTitulo.text = obtenerSaludo()
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)


        btnRegistrar.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            val emailValido = validarEmail(email, tilEmail)
            val passwordValida = validarPassword(password, tilPassword)

            if (emailValido && passwordValida) {
                // Ambas validaciones fueron exitosas
                Toast.makeText(this, "Registro exitoso\n  ¡Bienvenido!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    // Función que evalúa la hora del sistema
    private fun obtenerSaludo(): String {
        val calendar = Calendar.getInstance()
        val hora12 = calendar.get(Calendar.HOUR)  // Devuelve de 0 a 11
        val esAM = calendar.get(Calendar.AM_PM) == Calendar.AM

        return when {
            // En la mañana (AM) entre 6:00 AM y 11:59 AM
            esAM && hora12 in 6..11 -> "¡Buenos días!"

            // En la tarde (PM) desde 12:00 PM (hora12 es 0) hasta 6:59 PM (hora12 es 6)
            !esAM && (hora12 == 0 || hora12 in 1..6) -> "¡Buenas tardes!"

            // Madrugada o noche (12:00 AM a 5:59 AM o 7:00 PM a 11:59 PM)
            else -> "¡Buenas noches!"
        }
    }


    // 1. Validación de Correo Electrónico
    private fun validarEmail(email: String, layout: TextInputLayout): Boolean {
        return when {
            email.isEmpty() -> {
                layout.error = "El correo no puede estar vacío"
                false
            }
            // Patterns.EMAIL_ADDRESS es el patrón oficial de Android para correos válidos
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                layout.error = "Ingresa un correo electrónico válido (ejemplo: usuario@correo.com)"
                false
            }
            else -> {
                layout.error = null // Limpia el error si es válido
                true
            }
        }
    }

    // 2. Validación de Contraseña
    private fun validarPassword(password: String, layout: TextInputLayout): Boolean {
        // Expresión regular que exige:
        // (?=.*[a-z]) -> al menos una minúscula
        // (?=.*[A-Z]) -> al menos una mayúscula
        // (?=.*[0-9]) -> al menos un número
        // (?=.*[@#$%^&+=!._-]) -> al menos un carácter especial
        // .{8,}       -> mínimo 8 caracteres
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!._-]).{8,}$")

        return when {
            password.isEmpty() -> {
                layout.error = "La contraseña no puede estar vacía"
                false
            }
            !password.any { it.isUpperCase() } -> {
                layout.error = "Debe incluir al menos una letra mayúscula"
                false
            }
            !password.any { it.isLowerCase() } -> {
                layout.error = "Debe incluir al menos una letra minúscula"
                false
            }
            !password.any { it.isDigit() } -> {
                layout.error = "Debe incluir al menos un número"
                false
            }
            !password.any { "@#$%^&+=!._-".contains(it) } -> {
                layout.error = "Debe incluir al menos un carácter especial (@#$%^&+=!._-)"
                false
            }
            password.length < 8 -> {
                layout.error = "Debe tener al menos 8 caracteres"
                false
            }
            else -> {
                layout.error = null // Limpia el error si cumple todos los requisitos
                true
            }
        }
    }
}