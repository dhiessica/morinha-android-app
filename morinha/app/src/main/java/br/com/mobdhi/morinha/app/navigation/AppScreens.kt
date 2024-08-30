package br.com.mobdhi.morinha.app.navigation

import br.com.mobdhi.morinha.domain.model.Pet
import kotlinx.serialization.Serializable

sealed class RootScreen(val route: String) {
    object HomeRoot : RootScreen(route = "home_root")
    object AuthRoot : RootScreen(route = "auth_root")
}
sealed class LeafScreens(val route: String) {
    object Home : LeafScreens(route = "home")
    object Login : LeafScreens(route = "login")
    object Register : LeafScreens(route = "register")
    object AddPet : LeafScreens(route = "add_pet")
}

@Serializable
data class VaccinesRoute(val pet: Pet)