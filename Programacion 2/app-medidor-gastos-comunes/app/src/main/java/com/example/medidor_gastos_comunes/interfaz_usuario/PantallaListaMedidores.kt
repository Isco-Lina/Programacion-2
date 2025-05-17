package com.example.medidor_gastos_comunes.interfaz_usuario

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.medidor_gastos_comunes.modelo.Medidor
import com.example.medidor_gastos_comunes.viewmodel.MedidorViewModel
import com.example.medidor_gastos_comunes.R
import java.text.NumberFormat
import java.util.Locale


@Composable
fun PantallaListaMedidores(
    viewModel: MedidorViewModel,
    navController: NavController
) {
    // Estado para almacenar los medidores obtenidos en la bd
    var medidores by remember { mutableStateOf(emptyList<Medidor>()) }

    // Cargar medidores desde la base de datos
    LaunchedEffect(Unit) {
        viewModel.obtenerMedidores { lista ->
            medidores = lista
        }
    }

    // Estructura principal de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titulo
        Text(
            text = stringResource(R.string.titulo_listado),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de medidores usando LazyColum
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(medidores) { medidor ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icono según el tipo de medidor
                        val icono = when (medidor.tipo.lowercase()) {
                            stringResource(R.string.tipo_agua).lowercase() -> R.drawable.agua
                            stringResource(R.string.tipo_luz).lowercase() -> R.drawable.luz
                            stringResource(R.string.tipo_gas).lowercase() -> R.drawable.gas
                            else -> R.drawable.otro
                        }
                        // Mostrar el icono del medidor
                        Icon(
                            painter = painterResource(id = icono),
                            contentDescription = medidor.tipo,
                            modifier = Modifier.size(40.dp).padding(end = 16.dp)
                        )
                        // Mostrar el detalle
                        Column(modifier = Modifier.weight(1f)) {
                            Text("${medidor.tipo} ${medidor.valor.toInt()}")
                            Text("${medidor.fecha}")
                        }

                        // Botones para Editar y Eliminar
                        Column(horizontalAlignment = Alignment.End) {
                            Button(
                                onClick = {
                                    navController.navigate("editar_medidor/${medidor.id}")
                                },
                                modifier = Modifier.padding(bottom = 4.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text(stringResource(R.string.boton_editar), color = MaterialTheme.colorScheme.onPrimary)
                            }

                            Button(
                                onClick = {
                                    viewModel.eliminarMedidor(medidor) {
                                        // Recargar la lista después de eliminar
                                        viewModel.obtenerMedidores { lista ->
                                            medidores = lista
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text(stringResource(R.string.boton_eliminar), color = MaterialTheme.colorScheme.onError)
                            }
                        }
                    }
                }
            }
        }

        // Botón flotante para agregar medidor
        FloatingActionButton(
            onClick = { navController.navigate("agregar_medidor") },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                painter = painterResource(id = R.drawable.mas),
                contentDescription = stringResource(R.string.boton_agregar_medidor),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}