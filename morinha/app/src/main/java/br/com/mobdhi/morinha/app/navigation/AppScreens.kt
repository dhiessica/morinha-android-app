package br.com.mobdhi.morinha.app.navigation

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Vaccine
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Serializable
object HomeRoot
@Serializable
object AuthRoot

@Serializable
object PetsRoute

@Serializable
object AddPetRoute

@Serializable
object AccountRoute

@Serializable
data class EditPetRoute(val pet: Pet)

@Serializable
data class VaccinesRoute(val pet: Pet)

@Serializable
data class AddVaccineRoute(val petId: String)

@Serializable
data class EditVaccineRoute(val vaccine: Vaccine)