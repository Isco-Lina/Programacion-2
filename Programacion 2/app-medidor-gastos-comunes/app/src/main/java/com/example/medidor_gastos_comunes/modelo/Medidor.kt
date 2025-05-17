package com.example.medidor_gastos_comunes.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entidad que representa "Medidor" en la bd
@Entity(tableName = "medidores")
data class Medidor(
    // PrimaryKey Crea ID unico automaticamente para cada registro.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tipo: String,  // Tipo de medidor (Agua, Luz, Gas)
    val fecha: String, //fecha de la lectura
    val valor: Double  //Valor registrado
)