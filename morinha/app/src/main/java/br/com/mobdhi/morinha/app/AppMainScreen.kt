package br.com.mobdhi.morinha.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.mobdhi.morinha.navigation.AppNavHost

@Composable
fun AppMainScreen(navHostController: NavHostController = rememberNavController()) {
    AppNavHost(navHostController = navHostController)
}