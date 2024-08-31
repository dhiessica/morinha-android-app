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
import br.com.mobdhi.morinha.account.AccountScreen
import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.pet.pets.PetsScreen
import br.com.mobdhi.morinha.auth.login.LoginScreen
import br.com.mobdhi.morinha.auth.register.RegisterScreen
import br.com.mobdhi.morinha.domain.model.Genre
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Specie
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.pet.addeditpet.AddPetScreen
import br.com.mobdhi.morinha.pet.addeditpet.AddPetViewModel
import br.com.mobdhi.morinha.vaccine.addeditvaccine.AddEditVaccineScreen
import br.com.mobdhi.morinha.vaccine.addeditvaccine.AddEditVaccineViewModel
import br.com.mobdhi.morinha.vaccine.vaccines.VaccinesScreen
import br.com.mobdhi.morinha.vaccine.vaccines.VaccinesViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get
import kotlin.math.log
import kotlin.reflect.typeOf

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    restartApp: () -> Unit
) {
    val authRepository: AuthRepository = get(AuthRepository::class.java)

    NavHost(
        navController = navHostController,
        startDestination = if (authRepository.hasUser) HomeRoot else AuthRoot
    ) {
        addAuthRoute(navHostController)
        addHomeRoute(navHostController, restartApp)
    }
}

private fun NavGraphBuilder.addAuthRoute(
    navController: NavController
) {
    navigation<AuthRoot>(
        startDestination = LoginRoute
    ) {
        showLogin(navController)
        showRegister(navController)
    }
}

private fun NavGraphBuilder.addHomeRoute(
    navController: NavController,
    restartApp: () -> Unit
) {
    navigation<HomeRoot>(
        startDestination = PetsRoute
    ) {
        showAccount(restartApp)
        showPets(navController)
        showAddPet(navController)
        showEditPet(navController)
        showVaccines(navController)
        showAddVaccine(navController)
        showEditVaccine(navController)
    }
}

private fun NavGraphBuilder.showLogin(
    navController: NavController
) {
    composable<LoginRoute> {
        val context = LocalContext.current

        LoginScreen(
            navigateToHomeScreen = {
                navController.navigate(HomeRoot) {
                    popUpTo(AuthRoot) {
                        inclusive = true
                    }
                }
            },
            navigateToForgotPasswordScreen = {
                Toast.makeText(
                    context,
                    "Funcionalidade ainda não implementada", Toast.LENGTH_LONG
                ).show()
            },
            navigateToRegisterScreen = { navController.navigate(RegisterRoute) },
        )
    }
}

private fun NavGraphBuilder.showRegister(
    navController: NavController
) {
    composable<RegisterRoute> {
        RegisterScreen(
            navigateToHomeScreen = {
                navController.navigate(PetsRoute) {
                    popUpTo(AuthRoot) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.showAccount(
    restartApp: () -> Unit
) {
    composable<AccountRoute> {
        AccountScreen(
            logOut = restartApp
        )
    }
}

private fun NavGraphBuilder.showPets(
    navController: NavController
) {
    composable<PetsRoute> {
        PetsScreen(
            navigateToAddPetScreen = { navController.navigate(AddPetRoute) },
            navigateToPetVaccinesScreen = { navController.navigate(VaccinesRoute(it)) },
            onAccountButtonClicked = { navController.navigate(AccountRoute)}
        )
    }
}

private fun NavGraphBuilder.showAddPet(
    navController: NavController
) {
    composable<AddPetRoute> {
        val viewModel = getViewModel<AddPetViewModel>(parameters = { parametersOf(Pet()) })

        AddPetScreen(
            viewModel = viewModel,
            navigateBack = { navController.popBackStack<PetsRoute>(inclusive = false) }
        )
    }
}

private fun NavGraphBuilder.showEditPet(
    navController: NavController
) {
    composable<EditPetRoute> (
        typeMap = mapOf(
            typeOf<Pet>() to serializedType<Pet>()
        )
    ) { backStackEntry ->
        val routeArguments = backStackEntry.toRoute<EditPetRoute>()

        val viewModel = getViewModel<AddPetViewModel>(parameters = { parametersOf(routeArguments.pet) })

        AddPetScreen(
            viewModel = viewModel,
            navigateBack = { navController.popBackStack<VaccinesRoute>(inclusive = false) }
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

        val routeArguments = backStackEntry.toRoute<VaccinesRoute>()

        val viewModel =
            getViewModel<VaccinesViewModel>(parameters = {
                parametersOf(
                    routeArguments.pet,
                    Vaccine()
                )
            })

        VaccinesScreen(
            viewModel = viewModel,
            pet = routeArguments.pet,
            navigateToEditPetScreen = { navController.navigate(EditPetRoute(it)) },
            navigateToVaccineEditScreen = { navController.navigate(EditVaccineRoute(it)) },
            navigateToAddVaccineScreen = { navController.navigate(AddVaccineRoute(it)) }
        )
    }
}

private fun NavGraphBuilder.showAddVaccine(
    navController: NavController
) {
    composable<AddVaccineRoute>(
        typeMap = mapOf(
            typeOf<Vaccine>() to serializedType<Vaccine>()
        )
    ) { backStackEntry ->

        val routeArguments = backStackEntry.toRoute<AddVaccineRoute>()

        val viewModel =
            getViewModel<AddEditVaccineViewModel>(parameters = {
                parametersOf(
                    routeArguments.petId,
                    Vaccine()
                )
            })

        AddEditVaccineScreen(
            viewModel = viewModel,
            navigateBack = { navController.popBackStack<VaccinesRoute>(inclusive = false) }
        )
    }
}

private fun NavGraphBuilder.showEditVaccine(
    navController: NavController
) {
    composable<EditVaccineRoute>(
        typeMap = mapOf(
            typeOf<Vaccine>() to serializedType<Vaccine>()
        )
    ) { backStackEntry ->

        val routeArguments = backStackEntry.toRoute<EditVaccineRoute>()
        val viewModel = getViewModel<AddEditVaccineViewModel>(parameters = {
                parametersOf(
                    routeArguments.vaccine.petId,
                    routeArguments.vaccine
                )
            })

        AddEditVaccineScreen(
            viewModel = viewModel,
            navigateBack = { navController.popBackStack<VaccinesRoute>(inclusive = false) }
        )
    }
}


