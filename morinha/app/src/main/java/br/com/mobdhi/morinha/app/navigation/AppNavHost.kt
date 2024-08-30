package br.com.mobdhi.morinha.app.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.pet.pets.HomeScreen
import br.com.mobdhi.morinha.auth.login.LoginScreen
import br.com.mobdhi.morinha.auth.register.RegisterScreen
import br.com.mobdhi.morinha.domain.model.Genre
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Specie
import br.com.mobdhi.morinha.pet.addpet.AddPetScreen
import br.com.mobdhi.morinha.vaccine.vaccines.VaccinesScreen
import org.koin.java.KoinJavaComponent.get
import kotlin.reflect.typeOf

@Composable
fun AppNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = getStartDestination()
    ) {
        addAuthRoute(navHostController)
        addHomeRoute(navHostController)
    }
}

private fun getStartDestination(): String {
    val authRepository: AuthRepository = get(AuthRepository::class.java)
    return if (authRepository.hasUser) RootScreen.HomeRoot.route
    else RootScreen.AuthRoot.route
}

private fun NavGraphBuilder.addAuthRoute(
    navController: NavController
) {
    navigation(
        route = RootScreen.AuthRoot.route,
        startDestination = LeafScreens.Login.route
    ) {
        showLogin(navController)
        showRegister(navController)
    }
}

private fun NavGraphBuilder.showLogin(
    navController: NavController
) {
    composable(
        route = LeafScreens.Login.route
    ) {
        val context = LocalContext.current

        LoginScreen(
            navigateToHomeScreen = {
                navController.navigate(RootScreen.HomeRoot.route) {
                    popUpTo(RootScreen.AuthRoot.route) {
                        inclusive = true
                    }
                }
            },
            navigateToForgotPasswordScreen = {
                Toast.makeText(context, "Funcionalidade ainda não implementada", Toast.LENGTH_LONG)
                    .show()

            },
            navigateToRegisterScreen = { navController.navigate(LeafScreens.Register.route) },
        )
    }
}

private fun NavGraphBuilder.showRegister(
    navController: NavController
) {
    composable(
        route = LeafScreens.Register.route
    ) {
        RegisterScreen(
            navigateToHomeScreen = {
                navController.navigate(RootScreen.HomeRoot.route) {
                    popUpTo(RootScreen.AuthRoot.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.addHomeRoute(
    navController: NavController
) {
    navigation(
        route = RootScreen.HomeRoot.route,
        startDestination = LeafScreens.Home.route
    ) {
        showHome(navController)
        showAddPet(navController)
        showVaccines(navController)
    }
}

private fun NavGraphBuilder.showHome(
    navController: NavController
) {
    composable(
        route = LeafScreens.Home.route
    ) {
        HomeScreen(
            navigateToAddPetScreen = { navController.navigate(LeafScreens.AddPet.route) },
            navigateToPetVaccinesScreen = {
                navController.navigate(VaccinesRoute(it))
            }
        )
    }
}

private fun NavGraphBuilder.showAddPet(
    navController: NavController
) {
    composable(
        route = LeafScreens.AddPet.route
    ) {
        AddPetScreen(
            navigateBack = { navController.navigateUp() }
        )
    }
}

private fun NavGraphBuilder.showVaccines(
    navController: NavController
) {
    composable<VaccinesRoute>(
        typeMap = mapOf(
            typeOf<Pet>() to serializedType<Pet>(),
            typeOf<Genre>() to serializedType<Genre>(),
            typeOf<Specie>() to serializedType<Specie>()
        )
    ) { backStackEntry ->
        val pet = backStackEntry.toRoute<VaccinesRoute>()
        VaccinesScreen(pet.pet)
    }
}

