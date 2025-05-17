package com.example.medidor_gastos_comunes.modelo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Interfaz @Dao que define métodos para interactuar con la tabla "medidores"
@Dao
interface MedidorDao {

    // Inserta un nuevo medidor en la base de datos
    @Insert
    suspend fun insertarMedidor(medidor: Medidor)

    // Actualiza un medidor existente
    @Update
    suspend fun actualizarMedidor(medidor: Medidor)

    // Elimina un medidor de la base de datos
    @Delete
    suspend fun eliminarMedidor(medidor: Medidor)

    // Obtiene un medidor específico por su ID
    @Query("SELECT * FROM medidores WHERE id = :medidorId")
    suspend fun obtenerMedidorPorId(medidorId: Int): Medidor?

    // Obtiene todos los medidores en la base de datos
    @Query("SELECT * FROM medidores")
    suspend fun obtenerTodosLosMedidores(): List<Medidor>
}