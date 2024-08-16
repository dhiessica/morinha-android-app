package br.com.mobdhi.morinha.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.domain.model.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val accountService: AuthRepository
) : ViewModel() {
    var uiState = MutableStateFlow(LoginUiState())
        private set

    fun onEmailChange(newValue: String) {
        uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        uiState.update { it.copy(password = newValue) }
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        accountService.login(email, password).collectLatest { result ->
            when (result) {
                is Response.Loading -> {
                    uiState.update { it.copy(isLoading = true) }
                }

                is Response.Success -> {
                    uiState.update {
                        it.copy(
                            isSuccess = true,
                            isLoading = false,
                            isError = ""
                        )
                    }
                }

                is Response.Error -> {
                    uiState.update {
                        it.copy(
                            isError = result.message ?: "Error with no message",
                            isSuccess = false,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }
}