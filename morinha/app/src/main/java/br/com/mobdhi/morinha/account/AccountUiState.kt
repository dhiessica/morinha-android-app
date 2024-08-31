package br.com.mobdhi.morinha.account

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import br.com.mobdhi.morinha.domain.model.User

sealed class AccountUiState(
    var user: User = User(),
    val message: String? = null
) {
    class Initial(user: User) : AccountUiState(user)
    class Success : AccountUiState()
    class Error(message: String?, user: User? = null) : AccountUiState(message = message)
    class Loading : AccountUiState()
}