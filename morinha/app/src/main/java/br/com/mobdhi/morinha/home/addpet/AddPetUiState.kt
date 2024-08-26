package br.com.mobdhi.morinha.home.addpet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import br.com.mobdhi.morinha.domain.model.Pet

sealed class AddPetUiState(
    var pet: MutableState<Pet> = mutableStateOf(Pet()),
    val message: String? = null
) {
    class Initial(pet: MutableState<Pet>) : AddPetUiState()
    class Success(pet: MutableState<Pet>?) : AddPetUiState()
    class Error(message: String?, pet: Pet? = null) : AddPetUiState()
    class Loading : AddPetUiState()
}