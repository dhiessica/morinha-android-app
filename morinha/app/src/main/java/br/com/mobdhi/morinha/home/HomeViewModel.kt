package br.com.mobdhi.morinha.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    var uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading())
        private set

    init {
        getPets()
    }

    fun getPets() = viewModelScope.launch {
        homeRepository.getPets().collectLatest { result ->
            when (result) {
                is Response.Loading -> {
                    uiState.update { HomeUiState.Loading() }
                }

                is Response.Success -> {
                    uiState.update { HomeUiState.Success(result.data) }
                }

                is Response.Error -> {
                    uiState.update { HomeUiState.Error(result.message ?: "Error with no message") }
                }
            }
        }
    }
}