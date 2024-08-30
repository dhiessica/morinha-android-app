package br.com.mobdhi.morinha.vaccine.vaccines

import br.com.mobdhi.morinha.domain.model.Vaccine

sealed class VaccinesUiState(
    val vaccinesList: List<Vaccine>? = null,
    val message: String? = null
) {
    class Success(vaccinesList: List<Vaccine>?) : VaccinesUiState(vaccinesList)
    class Error(message: String, vaccinesList: List<Vaccine>? = null) : VaccinesUiState(vaccinesList, message)
    class Loading: VaccinesUiState()
}