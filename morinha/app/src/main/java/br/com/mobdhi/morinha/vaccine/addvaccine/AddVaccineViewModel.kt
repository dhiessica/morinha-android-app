package br.com.mobdhi.morinha.vaccine.addvaccine

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.domain.repository.VaccinesRepository
import br.com.mobdhi.morinha.pet.addpet.AddPetUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddVaccineViewModel(
    private val vaccinesRepository: VaccinesRepository
): ViewModel() {

    var uiState = MutableStateFlow<AddVaccineUiState>(
        AddVaccineUiState.Initial(
            vaccine = mutableStateOf(Vaccine()),
            isEntryValid = mutableStateOf(false)
        )
    )
        private set

    fun addVaccine(vaccine: Vaccine = uiState.value.vaccine.value) = viewModelScope.launch {
        if (validateInput(vaccine)) {
            vaccinesRepository.addVaccine(vaccine).collectLatest { result ->
                when (result) {
                    is Response.Loading -> {
                        uiState.update { AddVaccineUiState.Loading() }
                    }
                    is Response.Success -> {
                        uiState.update { AddVaccineUiState.Success() }
                    }
                    is Response.Error -> {
                        uiState.update { AddVaccineUiState.Error(result.message) }
                    }
                }
            }
        }
    }

    fun updateUiState(vaccine: Vaccine) {
        uiState.value.vaccine.value = vaccine
        uiState.value.isEntryValid.value = validateInput(vaccine)
    }

    private fun validateInput(state: Vaccine = uiState.value.vaccine.value): Boolean {
        return with(state) {
            name.isNotBlank() && applicationDate.isNotBlank() && batchNumber.isNotBlank() && manufacturer.isNotBlank()
                    && nextApplicationDate.isNotBlank() && veterinarian.isNotBlank()
        }
    }
}