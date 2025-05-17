package com.example.medidor_gastos_comunes

import android.app.Application
import com.example.medidor_gastos_comunes.modelo.MedidorDatabase

class MedidorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializa la base de datos al iniciar la aplicación
        // Esto asegura que la base de datos esté disponible para todas las operaciones.
        MedidorDatabase.Companion.obtenerBaseDeDatos(this)
    }
}