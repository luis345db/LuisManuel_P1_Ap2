@file:OptIn(ExperimentalMaterialApi::class)

package edu.ucne.luismanuel_p1_ap2.presentation.venta

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.luismanuel_p1_ap2.data.local.entities.VentasEntity
import edu.ucne.luismanuel_p1_ap2.presentation.venta.VentaViewModel.UiState
import kotlinx.coroutines.delay

@Composable
fun VentaListScreen(
    viewModel: VentaViewModel = hiltViewModel(),

    goToVenta: (Int) -> Unit,
    goToAddVenta: () -> Unit,

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProductoListBodyScreen(
        uiState = uiState,
        goToVenta = goToVenta,
        goToAddVenta = goToAddVenta,
        onDeleteVenta = {venta ->
            viewModel.delete(venta)
        }
    )
}

@Composable
fun ProductoListBodyScreen(
    uiState: UiState,
    goToVenta: (Int) -> Unit,
    goToAddVenta: () -> Unit,
    onDeleteVenta: (VentasEntity) -> Unit
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
                items(uiState.ventas) {venta ->
                    SwipeToDeleteContainer(
                        item = venta,
                        onDeleteVenta = onDeleteVenta
                    ) { ventaItem, isSwipeable ->
                        ProductoRow(
                            it = ventaItem,
                            goToVenta = goToVenta,
                            isSwipeable = isSwipeable
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun ProductoRow(
    it: VentasEntity,
    goToVenta: (Int) -> Unit,
    isSwipeable: Boolean = true
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable(enabled = isSwipeable) { goToVenta(it.ventaId ?: 0) }

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



@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDeleteVenta: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T, Boolean) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }
    val state = rememberDismissState()

    LaunchedEffect(key1 = state.currentValue) {
        if (state.currentValue == DismissValue.DismissedToStart && !isRemoved) {
            isRemoved = true
            delay(animationDuration.toLong())
            onDeleteVenta(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBackground(swipeDismissState = state)
            },
            dismissContent = { content(item, state.currentValue != DismissValue.DismissedToStart) },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}

@Composable
fun DeleteBackground(swipeDismissState: DismissState) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}


