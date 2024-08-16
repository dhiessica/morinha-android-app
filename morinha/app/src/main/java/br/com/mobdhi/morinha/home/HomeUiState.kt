package br.com.mobdhi.morinha.home

import br.com.mobdhi.morinha.domain.model.Pet

sealed class HomeUiState(
    val petList: List<Pet>? = null,
    val message: String? = null
) {
    class Success(petList: List<Pet>?) : HomeUiState(petList)
    class Error(message: String, petList: List<Pet>? = null) : HomeUiState(petList, message)
    class Loading: HomeUiState()
}
