package br.com.mobdhi.morinha.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.com.mobdhi.morinha.home.HomeScreen
import br.com.mobdhi.morinha.login.LoginScreen
import br.com.mobdhi.morinha.register.RegisterScreen

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = RootScreen.AuthRoot.route
    ) {
        addAuthRoute(navHostController)
        addHomeRoute()
    }
}

private fun NavGraphBuilder.addAuthRoute(
    navController: NavController
) {
    navigation(
        route = RootScreen.AuthRoot.route,
        startDestination = LeafScreens.Login.route
    ) {
        showLogin(navController)
        showRegister()
    }
}

private fun NavGraphBuilder.showLogin(
    navController: NavController
) {
    composable(
        route = LeafScreens.Login.route
    ) {
        LoginScreen(
            navigateToHomeScreen = {
                navController.navigate(RootScreen.HomeRoot.route) {
                    popUpTo(RootScreen.AuthRoot.route) {
                        inclusive = true
                    }
                }
            },
            navigateToForgotPasswordScreen = {/*TODO*/ },
            navigateToRegisterScreen = { navController.navigate(LeafScreens.Register.route) },
        )
    }
}

private fun NavGraphBuilder.showRegister() {
    composable(
        route = LeafScreens.Register.route
    ) {
        RegisterScreen()
    }
}

private fun NavGraphBuilder.addHomeRoute() {
    navigation(
        route = RootScreen.HomeRoot.route,
        startDestination = LeafScreens.Home.route
    ) {
        showHome()
    }
}

private fun NavGraphBuilder.showHome() {
    composable(
        route = LeafScreens.Home.route
    ) {
        HomeScreen()
    }
}

