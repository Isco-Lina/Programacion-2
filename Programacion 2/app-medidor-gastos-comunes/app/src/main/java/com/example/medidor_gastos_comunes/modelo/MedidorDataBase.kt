package com.example.medidor_gastos_comunes.modelo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Clase que define la base de datos
@Database(entities = [Medidor::class], version = 1, exportSchema = false)
abstract class MedidorDatabase : RoomDatabase() {
    // Retornar el DAO para interactuar con la bd
    abstract fun medidorDao(): MedidorDao

    companion object {
        @Volatile
        private var INSTANCE: MedidorDatabase? = null

        // Funcion para obtener una unica instancia de la bd (Singleton)
        fun obtenerBaseDeDatos(context: Context): MedidorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedidorDatabase::class.java,
                    "medidor_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}