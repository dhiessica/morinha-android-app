package br.com.mobdhi.morinha.home.pets

import br.com.mobdhi.morinha.domain.model.Pet

sealed class PetsUiState(
    val petList: List<Pet>? = null,
    val message: String? = null
) {
    class Success(petList: List<Pet>?) : PetsUiState(petList)
    class Error(message: String, petList: List<Pet>? = null) : PetsUiState(petList, message)
    class Loading: PetsUiState()
}
