package br.com.mobdhi.morinha.auth.register

data class RegisterUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)