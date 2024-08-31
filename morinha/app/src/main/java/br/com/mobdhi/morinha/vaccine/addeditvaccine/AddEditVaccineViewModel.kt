package br.com.mobdhi.morinha.vaccine.addeditvaccine

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

    var uiState = MutableStateFlow<AddEditVaccineUiState>(
        AddEditVaccineUiState.Initial(
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

    private fun addVaccine(vaccine: Vaccine = uiState.value.vaccine.value) = viewModelScope.launch {
        if (validateInput()) {
            vaccinesRepository.addVaccine(vaccine.copy(petId = petId)).collectLatest { result ->
                when (result) {
                    is Response.Loading -> {
                        uiState.update { AddEditVaccineUiState.Loading() }
                    }

                    is Response.Success -> {
                        uiState.update { AddEditVaccineUiState.Success() }
                    }

                    is Response.Error -> {
                        uiState.update { AddEditVaccineUiState.Error(result.message) }
                    }
                }
            }
        }
    }

    private fun updateVaccine(vaccine: Vaccine = uiState.value.vaccine.value) = viewModelScope.launch {
        if (validateInput(vaccine.copy(petId = petId))) {
            vaccinesRepository.updateVaccine(vaccine).collectLatest { result ->
                when (result) {
                    is Response.Loading -> {
                        uiState.update { AddEditVaccineUiState.Loading() }
                    }

                    is Response.Success -> {
                        uiState.update { AddEditVaccineUiState.Success() }
                    }

                    is Response.Error -> {
                        uiState.update { AddEditVaccineUiState.Error(result.message) }
                    }
                }
            }
        }
    }

    fun deleteVaccine(vaccine: Vaccine = uiState.value.vaccine.value) = viewModelScope.launch {
        vaccinesRepository.deleteVaccine(vaccine).collectLatest { result ->
            when (result) {
                is Response.Loading -> {
                    uiState.update { AddEditVaccineUiState.Loading() }
                }

                is Response.Success -> {
                    uiState.update { AddEditVaccineUiState.Success() }
                }

                is Response.Error -> {
                    uiState.update { AddEditVaccineUiState.Error(result.message) }
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