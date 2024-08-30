package br.com.mobdhi.morinha.pet.addpet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.repository.PetRepository
import br.com.mobdhi.morinha.pet.data.PetRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddPetViewModel(
    private val petRepository: PetRepository
): ViewModel() {
    var uiState = MutableStateFlow<AddPetUiState>(
        AddPetUiState.Initial(
            pet = mutableStateOf(Pet()),
            isEntryValid = mutableStateOf(false)
        )
    )
        private set

    fun addPet(pet: Pet = uiState.value.pet.value) = viewModelScope.launch {
        if (validateInput(pet)) {
            petRepository.addPet(pet).collectLatest { result ->
                when (result) {
                    is Response.Loading -> {
                        uiState.update { AddPetUiState.Loading() }
                    }
                    is Response.Success -> {
                        uiState.update { AddPetUiState.Success() }
                    }
                    is Response.Error -> {
                        uiState.update { AddPetUiState.Error(result.message) }
                    }
                }
            }
        }
    }
    fun updateUiState(pet: Pet) {
        uiState.value.pet.value = pet
        uiState.value.isEntryValid.value = validateInput(pet)
    }

    private fun validateInput(state: Pet = uiState.value.pet.value): Boolean {
        return with(state) {
            name.isNotBlank() && specie.id != null && breed.isNotBlank() && bornDate.isNotBlank()
                    && genre.id != null && weight.isNotBlank() && color.isNotBlank()
        }
    }
}