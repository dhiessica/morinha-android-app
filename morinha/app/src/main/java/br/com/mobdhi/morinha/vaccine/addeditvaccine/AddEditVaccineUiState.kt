package br.com.mobdhi.morinha.vaccine.addeditvaccine

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import br.com.mobdhi.morinha.domain.model.Vaccine

sealed class AddEditVaccineUiState(
    var vaccine: MutableState<Vaccine> = mutableStateOf(Vaccine()),
    var isEntryValid: MutableState<Boolean> = mutableStateOf(false),
    val message: String? = null
) {
    class Initial(vaccine: MutableState<Vaccine>, isEntryValid: MutableState<Boolean>) : AddEditVaccineUiState(vaccine, isEntryValid)
    class Success : AddEditVaccineUiState()
    class Error(message: String?, vaccine: Vaccine? = null) : AddEditVaccineUiState(message = message)
    class Loading : AddEditVaccineUiState()
}