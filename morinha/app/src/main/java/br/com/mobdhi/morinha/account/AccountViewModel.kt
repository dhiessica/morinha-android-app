package br.com.mobdhi.morinha.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.model.User
import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.pet.addeditpet.AddPetUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    private val auth: AuthRepository
) : ViewModel() {
    var uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading())
        private set

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() = viewModelScope.launch {
        auth.currentUser.collect {user ->
            uiState.update { AccountUiState.Initial(user) }
        }
    }

    fun logoutUser() = viewModelScope.launch {
        auth.logout().collectLatest { result ->
            when (result) {
                is Response.Loading -> {
                    uiState.update { AccountUiState.Loading() }
                }
                is Response.Success -> {
                    uiState.update { AccountUiState.Success() }
                }
                is Response.Error -> {
                    uiState.update { AccountUiState.Error(result.message) }
                }
            }
        }
    }
}