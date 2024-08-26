package br.com.mobdhi.morinha.home.addpet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import br.com.mobdhi.morinha.domain.model.Pet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AddPetViewModel: ViewModel() {
    var uiState = MutableStateFlow<AddPetUiState>(AddPetUiState.Initial(mutableStateOf(Pet())))
        private set

    fun addPet(pet: Pet = uiState.value.pet.value) {

    }
    fun updateUiState(pet: Pet) {
        uiState.value.pet.value = pet
    }

    private fun validateInput(state: Pet? = uiState.value.pet.value): Boolean {
        return with(uiState.value.pet.value) {
            name.isNotBlank() && specie.id != null && breed.isNotBlank() && bornDate.isNotBlank()
                    && genre.id != null && weight.isNotBlank() && color.isNotBlank()
        }
    }
}