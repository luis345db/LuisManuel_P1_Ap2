package edu.ucne.luismanuel_p1_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object listScreen : Screen()

    @Serializable
    data class registroScreen(val id: Int) : Screen()

}