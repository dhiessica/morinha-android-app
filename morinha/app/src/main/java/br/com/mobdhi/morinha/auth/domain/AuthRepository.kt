package br.com.mobdhi.morinha.auth.domain

import br.com.mobdhi.morinha.auth.domain.User
import br.com.mobdhi.morinha.domain.model.Response
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUserId: String

    val hasUser: Boolean

    val currentUser: Flow<User>

    fun login(email: String, password: String): Flow<Response<AuthResult>>
    fun register(email: String, password: String): Flow<Response<AuthResult>>

}