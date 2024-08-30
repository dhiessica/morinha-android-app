package br.com.mobdhi.morinha.vaccine.addvaccine

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.domain.repository.VaccinesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditVaccineViewModel(
    private val petId: String,
    private val currentVaccine: Vaccine,
    private val vaccinesRepository: VaccinesRepository
) : ViewModel() {

    var uiState = MutableStateFlow<AddVaccineUiState>(
        AddVaccineUiState.Initial(
            vaccine = mutableStateOf(Vaccine()),
            isEntryValid = mutableStateOf(false)
        )
    )
        private set

    init {
        updateUiState(currentVaccine)
    }
    fun addEditVaccine(vaccine: Vaccine = uiState.value.vaccine.value) {
        if (vaccine.id.isNotBlank()) updateVaccine(vaccine)
        else addVaccine(vaccine)
    }

    private fun updateVaccine(vaccine: Vaccine = uiState.value.vaccine.value) = viewModelScope.launch {
        if (validateInput(vaccine.copy(petId = petId))) {
            vaccinesRepository.updateVaccine(vaccine).collectLatest { result ->
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

    private fun addVaccine(vaccine: Vaccine = uiState.value.vaccine.value) = viewModelScope.launch {
        if (validateInput(vaccine.copy(petId = petId))) {
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
        uiState.value.isEntryValid.value = if (uiState.value.vaccine.value.id.isNotBlank()) {
            validateInput(vaccine) && validateInputChanged(vaccine)
        } else validateInput(vaccine)
    }

    private fun validateInput(state: Vaccine = uiState.value.vaccine.value): Boolean {
        return with(state) {
            name.isNotBlank()
                    && applicationDate.isNotBlank() && batchNumber.isNotBlank()
                    && manufacturer.isNotBlank() && nextApplicationDate.isNotBlank()
                    && veterinarian.isNotBlank()
        }
    }

    private fun validateInputChanged(state: Vaccine = uiState.value.vaccine.value): Boolean {
        return with(state) {
            name != currentVaccine.name
                    || applicationDate != currentVaccine.applicationDate
                    || batchNumber != currentVaccine.batchNumber
                    || manufacturer != currentVaccine.manufacturer
                    || nextApplicationDate != currentVaccine.nextApplicationDate
                    || veterinarian != currentVaccine.veterinarian
                    || observation != currentVaccine.observation
        }
    }
}