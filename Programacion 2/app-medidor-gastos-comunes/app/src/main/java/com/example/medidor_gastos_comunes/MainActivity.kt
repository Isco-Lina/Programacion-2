package com.example.medidor_gastos_comunes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medidor_gastos_comunes.interfaz_usuario.PantallaListaMedidores
import com.example.medidor_gastos_comunes.interfaz_usuario.PantallaAgregarMedidor
import com.example.medidor_gastos_comunes.viewmodel.MedidorViewModel

// Clase principal de la app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedidoresApp()
        }
    }
}

// Funcion principal que configura la navegacion y las pantallas.
@Composable
fun MedidoresApp() {
    val navController = rememberNavController()
    val viewModel: MedidorViewModel = viewModel()

    // Configura el NavHost para definir las rutas y pantallas disponibles
    NavHost(navController = navController, startDestination = "lista_medidores") {
        composable("lista_medidores") {
            PantallaListaMedidores(navController = navController, viewModel = viewModel)
        }
        composable("agregar_medidor") {
            PantallaAgregarMedidor(navController = navController, viewModel = viewModel)
        }
        composable("editar_medidor/{medidorId}") { backStackEntry ->
            val medidorId = backStackEntry.arguments?.getString("medidorId")?.toIntOrNull()
            if (medidorId != null) {
                PantallaAgregarMedidor(navController = navController, viewModel = viewModel, medidorId = medidorId)
            }
        }
    }
}