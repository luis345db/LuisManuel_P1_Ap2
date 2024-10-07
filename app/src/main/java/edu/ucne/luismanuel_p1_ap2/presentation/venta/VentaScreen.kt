package edu.ucne.luismanuel_p1_ap2.presentation.venta

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.luismanuel_p1_ap2.ui.theme.LuisManuel_P1_Ap2Theme


@Composable
fun VentaScreen(
    viewModel: VentaViewModel = hiltViewModel(),
    goBack: () -> Unit,
    ventaId: Int
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = ventaId) {
        if (ventaId != 0) {
            viewModel.selectedVenta(ventaId)
        }
    }

    VentaBodyScreen(
        uiState = uiState,
        onNombreEmpresaChange = viewModel::onNombreEmpresaChange,
        onGalonesChange= viewModel::onGalonesChange,
        onVentasIdChange = viewModel::onVentasIdChange,
        onDescuentoGalonChange= viewModel::onDescuentoGalonChange,
        onPrecioChange= viewModel::onPrecioChange,
        saveVenta = viewModel::save,
        deleteVenta = viewModel::delete,
        nuevoVenta = viewModel::nuevo,
        goBack = goBack,
        ventaId = ventaId
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaBodyScreen(
    uiState: VentaViewModel.UiState,
    onNombreEmpresaChange: (String) -> Unit,
    onGalonesChange: (Int) -> Unit,
    onDescuentoGalonChange: (Double) -> Unit,
    onPrecioChange: (Double) -> Unit,
    onVentasIdChange: (Int) -> Unit,
    saveVenta: () -> Unit,
    ventaId: Int,
    deleteVenta: () -> Unit,
    nuevoVenta: () -> Unit,

    goBack: () -> Unit,

    ){

    var nombreEmpresaError by remember { mutableStateOf(false) }
    var galonesError by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Registro de Ventas")
                },
                navigationIcon = {
                    IconButton(onClick = goBack ) {
                        Icon(imageVector = Icons.Filled.Menu,
                            contentDescription = "Menú"
                        )
                    }
                }
            )


        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Productos",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Nombre Empresa")
                        },
                        value = uiState.nombreEmpresa?: "",
                        onValueChange = { onNombreEmpresaChange
                            nombreEmpresaError = it.isBlank()

                        },
                        isError = nombreEmpresaError,
                        singleLine = true
                    )
                    if (nombreEmpresaError) {
                        Text(
                            text = "Este campo es obligatorio",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }


                    OutlinedTextField(
                        label = { Text(text = "Galones") },
                        value = uiState.galones?.toString() ?: "",
                        onValueChange = { newValue ->

                            val intValue = newValue.toIntOrNull()
                            if (intValue != null && intValue > 0) {
                                onGalonesChange(intValue)
                                galonesError = false
                            }
                            else{
                                galonesError = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number // Mostramos un teclado numérico
                        ),
                        isError = galonesError,
                        singleLine = true
                    )
                    if (galonesError) {
                        Text(
                            text = "Los galones deben ser mayores a 0",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }


                    OutlinedTextField(
                        label = { Text(text = "Descuento por galon") },
                        value = uiState.descuentoGalon.toString() ?: "", // Convertimos el valor a String
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true
                    )


                    OutlinedTextField(
                        label = { Text(text = "Precio") },
                        value = uiState.precio.toString() ?: "", // Convertimos el valor a String
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true
                    )

                    OutlinedTextField(
                        label = { Text(text = "Total Descontado") },
                        value = uiState.totalDescontado?.toString() ?: "", // Convertimos el valor a String
                        onValueChange = {onDescuentoGalonChange},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true
                    )

                    OutlinedTextField(
                        label = { Text(text = "Total") },
                        value = uiState.total?.toString() ?: "", // Convertimos el valor a String
                        onValueChange = {onPrecioChange},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    uiState.errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }

                    OutlinedButton(
                        onClick = {
                            nuevoVenta()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "new button"
                        )
                        Text(text = "Nuevo")
                    }
                    val scope = rememberCoroutineScope()
                    OutlinedButton(

                        onClick = {


                            nombreEmpresaError = uiState.nombreEmpresa.isNullOrBlank()
                            galonesError = uiState.galones == null || uiState.galones <= 0

                            if(!nombreEmpresaError && !galonesError){
                            saveVenta()
                            goBack()
                                }
                        }

                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "save button"
                        )
                        Text(text = "Guardar")
                    }

                    if (ventaId != 0) {
                        Button(
                            onClick = {
                                deleteVenta()
                                goBack()

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar Producto"
                            )
                            Text("Eliminar")


                        }


                    }
                }

            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductoScreenPreview(){
    LuisManuel_P1_Ap2Theme(){
        VentaScreen(
            ventaId = 0,
            goBack = {}
        )
    }
}