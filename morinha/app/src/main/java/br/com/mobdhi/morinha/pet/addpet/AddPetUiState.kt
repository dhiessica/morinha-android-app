package br.com.mobdhi.morinha.pet.addpet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import br.com.mobdhi.morinha.domain.model.Pet

sealed class AddPetUiState(
    var pet: MutableState<Pet> = mutableStateOf(Pet()),
    var isEntryValid: MutableState<Boolean> = mutableStateOf(false),
    val message: String? = null
) {
    class Initial(pet: MutableState<Pet>, isEntryValid: MutableState<Boolean>) : AddPetUiState(pet, isEntryValid)
    class Success() : AddPetUiState()
    class Error(message: String?, pet: Pet? = null) : AddPetUiState()
    class Loading : AddPetUiState()
}