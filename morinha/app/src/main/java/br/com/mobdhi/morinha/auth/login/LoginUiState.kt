package br.com.mobdhi.morinha.auth.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: String = "",
    val email: String = "",
    val password: String = ""
)