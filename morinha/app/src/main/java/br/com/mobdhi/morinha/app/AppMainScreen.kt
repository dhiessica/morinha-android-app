package br.com.mobdhi.morinha.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.mobdhi.morinha.app.navigation.AppNavHost

@Composable
fun AppMainScreen(navHostController: NavHostController = rememberNavController(), restartApp: () -> Unit) {
    AppNavHost(navHostController = navHostController, restartApp = restartApp)
}