package com.example.medidor_gastos_comunes.interfaz_usuario

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medidor_gastos_comunes.modelo.Medidor
import com.example.medidor_gastos_comunes.viewmodel.MedidorViewModel
import androidx.navigation.NavController
import com.example.medidor_gastos_comunes.R

@Composable
fun PantallaAgregarMedidor(
    viewModel: MedidorViewModel = viewModel(),
    navController: NavController,
    medidorId: Int? = null
) {
    // Cargar los tipos de medidor usando stringResource
    val tiposMedidor = listOf(
        stringResource(R.string.tipo_agua),
        stringResource(R.string.tipo_luz),
        stringResource(R.string.tipo_gas)
    )

    // Estado para los campos del formulario
    var tipoSeleccionado by remember { mutableStateOf(tiposMedidor.first()) }
    var fecha by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }

    // Cargar los datos si se está editando
    LaunchedEffect(medidorId) {
        if (medidorId != null) {
            val medidor = viewModel.obtenerMedidorPorId(medidorId)
            if (medidor != null) {
                tipoSeleccionado = medidor.tipo
                fecha = medidor.fecha
                valor = medidor.valor.toString()
            }
        }
    }

    // Estructura principal del formulario
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.titulo_registro),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo para el valor del medidor
        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text(stringResource(R.string.placeholder_valor)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para la fecha del registro
        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text(stringResource(R.string.placeholder_fecha)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Selector de tipo de medidor
        Text(stringResource(R.string.placeholder_medidor), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        tiposMedidor.forEach { tipo ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = tipoSeleccionado == tipo,
                    onClick = { tipoSeleccionado = tipo }
                )
                Text(tipo, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para guardar o actualizar medidor
        Button(
            onClick = {
                if (fecha.isNotBlank() && valor.isNotBlank()) {
                    if (medidorId == null) {
                        viewModel.insertarMedidor(Medidor(tipo = tipoSeleccionado, fecha = fecha, valor = valor.toDouble())) {
                            navController.popBackStack()
                        }
                    } else {
                        viewModel.actualizarMedidor(
                            Medidor(id = medidorId, tipo = tipoSeleccionado, fecha = fecha, valor = valor.toDouble())
                        ) {
                            navController.popBackStack()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                if (medidorId == null) stringResource(R.string.boton_registrar)
                else stringResource(R.string.boton_guardar),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}