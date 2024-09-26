package edu.ucne.luismanuel_p1_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object VentaList : Screen()

    @Serializable
    data class Venta(val ventaid: Int) : Screen()

}