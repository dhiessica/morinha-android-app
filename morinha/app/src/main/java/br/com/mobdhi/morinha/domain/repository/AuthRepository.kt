package br.com.mobdhi.morinha.domain.repository

import br.com.mobdhi.morinha.domain.model.User
import br.com.mobdhi.morinha.domain.model.Response
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    /**
     * @param currentUserId é uma [String] com o ID do usuário logado atualmente.
     */
    val currentUserId: String

    /**
     * @param hasUser é um [Boolean] que retorna se há um usuário logado atualmente.
     */
    val hasUser: Boolean

    /**
     * @param currentUser é um [Flow] que retorna os dados do usuário logado atualmente como um [User].
     */
    val currentUser: Flow<User>

    /**
     * Função para logar um usuário
     *
     * @param email é uma [String] com o email do usuário que vai efetuar login.
     * @param password é um [String] com a senha do usuário que vai efetuar login.
     */
    fun login(email: String, password: String): Flow<Response<AuthResult>>

    /**
     * Função para registrar um usuário
     *
     * @param email é uma [String] com o email do usuário que será registrado.
     * @param password é um [String] com a senha do usuário que será registrado.
     */
    fun register(email: String, password: String): Flow<Response<AuthResult>>

    /**
     * Função para deslogar o usuário atual
     */
    fun logout(): Flow<Response<String>>
}