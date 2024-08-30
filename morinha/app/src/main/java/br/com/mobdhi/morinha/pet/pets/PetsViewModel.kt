package br.com.mobdhi.morinha.pet.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.repository.PetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetsViewModel(
    private val petRepository: PetRepository
) : ViewModel() {

    var uiState = MutableStateFlow<PetsUiState>(PetsUiState.Loading())
        private set

    fun getPets() = viewModelScope.launch {
        petRepository.getPets().collectLatest { result ->
            when (result) {
                is Response.Loading -> {
                    uiState.update { PetsUiState.Loading() }
                }

                is Response.Success -> {
                    uiState.update { PetsUiState.Success(result.data) }
                }

                is Response.Error -> {
                    uiState.update { PetsUiState.Error(result.message ?: "Error with no message") }
                }
            }
        }
    }
}