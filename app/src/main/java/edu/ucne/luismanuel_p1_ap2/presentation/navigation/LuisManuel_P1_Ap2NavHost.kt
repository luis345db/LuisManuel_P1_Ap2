package edu.ucne.luismanuel_p1_ap2.presentation.navigation

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun LuisManuel_P1_Ap2NavHost(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.listScreen

    ){
        composable<Screen.listScreen> {
                OutlinedButton(
                    onClick = {navHostController.navigate(Screen.registroScreen(0))}
                ) {
                    Text(
                        text = "Ir a la Segunda Pantalla"
                    )
                }
        }

        composable<Screen.registroScreen> {
            Text(
                text = "Estamos en la Segunda Pantalla"
            )
        }


    }
}