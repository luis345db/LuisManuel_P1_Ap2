package edu.ucne.luismanuel_p1_ap2.presentation.venta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.luismanuel_p1_ap2.data.local.entities.VentasEntity
import edu.ucne.luismanuel_p1_ap2.presentation.venta.VentaViewModel.UiState

@Composable
fun VentaListScreen(
    viewModel: VentaViewModel = hiltViewModel(),

    goToVenta: (Int) -> Unit,
    goToAddVenta: () -> Unit,
    onEditVenta: () -> Unit,
    onDeleteVenta: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProductoListBodyScreen(
        uiState,
        goToVenta,
        goToAddVenta
    )
}

@Composable
fun ProductoListBodyScreen(
    uiState: UiState,
    goToVenta: (Int) -> Unit,
    goToAddVenta: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = goToAddVenta,

                ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Añadir Venta"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Lista de Ventas")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.ventas) {
                    ProductoRow(
                        it,
                        goToVenta,
                        goToAddVenta
                    )
                }
            }
        }
    }

}


@Composable
fun ProductoRow(
    it: VentasEntity,
    goToVenta: (Int) -> Unit,
    goToAddVenta: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { goToVenta(it.ventaId ?: 0) }

    ){
        Text(
            text = it.nombreEmpresa,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = it.galones.toString(),
            modifier = Modifier.weight((2f))
        )
        Text(
            text = it.descuentoGalon.toString(),
            modifier = Modifier.weight((2f))
        )
        Text(
            text = it.precio.toString(),
            modifier = Modifier.weight(2f)
        )
        Text(
            text = it.totalDescontado.toString(),
            modifier = Modifier.weight((2f))
        )
        Text(
            text = it.total.toString(),
            modifier = Modifier.weight((2f))
        )

    }
    HorizontalDivider()
}





