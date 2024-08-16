package br.com.mobdhi.morinha.auth

import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.domain.model.User
import br.com.mobdhi.morinha.domain.model.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
) : AuthRepository {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override fun login(email: String, password: String): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = auth.signInWithEmailAndPassword(email, password).await()
            emit(Response.Success(data = result))
        }.catch {
            emit(
                Response.Error(
                    message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
                )
            )
        }
    }

    override fun register(email: String, password: String): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            emit(Response.Success(result))
        }.catch {
            emit(
                Response.Error(
                    message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
                )
            )
        }
    }
}