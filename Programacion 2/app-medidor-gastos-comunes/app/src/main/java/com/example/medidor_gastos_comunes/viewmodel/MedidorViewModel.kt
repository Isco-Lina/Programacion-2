package com.example.medidor_gastos_comunes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.medidor_gastos_comunes.modelo.Medidor
import com.example.medidor_gastos_comunes.modelo.MedidorDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Clase ViewModel que maneja la lógica de negocio para los medidores
class MedidorViewModel(application: Application) : AndroidViewModel(application) {

    // Referencia al DAO para interactuar con la base de datos
    private val medidorDao = MedidorDatabase.obtenerBaseDeDatos(application).medidorDao()

    // Funcion o metodo para insertar un nuevo medidor a la bd
    fun insertarMedidor(medidor: Medidor, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            medidorDao.insertarMedidor(medidor)
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    // Funcion para actualizar un medidor existente
    fun actualizarMedidor(medidor: Medidor, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            medidorDao.actualizarMedidor(medidor)
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    // Funcion para eliminar un medidor
    fun eliminarMedidor(medidor: Medidor, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            medidorDao.eliminarMedidor(medidor)
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    // Funcion para obtener todos los medidores
    fun obtenerMedidores(callback: (List<Medidor>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val medidores = medidorDao.obtenerTodosLosMedidores()
            withContext(Dispatchers.Main) {
                callback(medidores)
            }
        }
    }
    // Funcion para obtener un medidor especifico por ID
    suspend fun obtenerMedidorPorId(id: Int): Medidor? {
        return withContext(Dispatchers.IO) {
            medidorDao.obtenerMedidorPorId(id)
        }
    }
}