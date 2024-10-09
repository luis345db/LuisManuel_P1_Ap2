package edu.ucne.luismanuel_p1_ap2.presentation.navigation

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.luismanuel_p1_ap2.presentation.venta.VentaListScreen
import edu.ucne.luismanuel_p1_ap2.presentation.venta.VentaScreen

@Composable
fun LuisManuel_P1_Ap2NavHost(
    navHost: NavHostController
){
    NavHost(
        navController = navHost,
        startDestination = Screen.VentaList

    ){
        composable<Screen.VentaList> {
            VentaListScreen(
                goToVenta = {
                    navHost.navigate(Screen.Venta(it))
                },
                goToAddVenta = {
                    navHost.navigate(Screen.Venta(0))
                },

            )
        }


        composable<Screen.Venta> {
            val args = it.toRoute<Screen.Venta>()
            VentaScreen(
                ventaId = args.ventaid,
                goBack = {
                    navHost.navigateUp()
                }
            )
        }


    }


}



