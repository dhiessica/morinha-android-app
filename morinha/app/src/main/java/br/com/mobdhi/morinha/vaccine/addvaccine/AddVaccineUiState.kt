package br.com.mobdhi.morinha.vaccine.addvaccine

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import br.com.mobdhi.morinha.domain.model.Vaccine

sealed class AddVaccineUiState(
    var vaccine: MutableState<Vaccine> = mutableStateOf(Vaccine()),
    var isEntryValid: MutableState<Boolean> = mutableStateOf(false),
    val message: String? = null
) {
    class Initial(vaccine: MutableState<Vaccine>, isEntryValid: MutableState<Boolean>) : AddVaccineUiState(vaccine, isEntryValid)
    class Success : AddVaccineUiState()
    class Error(message: String?, vaccine: Vaccine? = null) : AddVaccineUiState()
    class Loading : AddVaccineUiState()
}