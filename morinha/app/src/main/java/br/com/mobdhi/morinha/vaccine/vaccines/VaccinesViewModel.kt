package br.com.mobdhi.morinha.vaccine.vaccines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.repository.VaccinesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VaccinesViewModel(
    private val vaccinesRepository: VaccinesRepository,
): ViewModel() {

    var uiState = MutableStateFlow<VaccinesUiState>(VaccinesUiState.Loading())
        private set

    fun getVaccines(pet: Pet) = viewModelScope.launch {
        vaccinesRepository.getVaccines(pet).collectLatest { result ->
            when (result) {
                is Response.Loading -> {
                    uiState.update { VaccinesUiState.Loading() }
                }

                is Response.Success -> {
                    uiState.update { VaccinesUiState.Success(result.data) }
                }

                is Response.Error -> {
                    uiState.update {
                        VaccinesUiState.Error(
                            result.message ?: "Error with no message"
                        )
                    }
                }
            }
        }
    }

}