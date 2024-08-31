package br.com.mobdhi.morinha.auth

import br.com.mobdhi.morinha.domain.repository.AuthRepository
import br.com.mobdhi.morinha.domain.model.User
import br.com.mobdhi.morinha.domain.model.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.time.delay

class AuthRepositoryImpl(
    private val crashlytics: FirebaseCrashlytics,
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
                    this.trySend(auth.currentUser?.let { User(it.uid, it.email?:"") } ?: User())
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
            val message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
            emit(Response.Error(message = message))
            crashlytics.recordException(IllegalArgumentException(message))
        }
    }

    override fun register(email: String, password: String): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            emit(Response.Success(result))
        }.catch {
            val message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
            emit(Response.Error(message = message))
            crashlytics.recordException(IllegalArgumentException(message))
        }
    }

    override fun logout(): Flow<Response<String>> {
        return flow {
            emit(Response.Loading())
            val result = auth.signOut()
            emit(Response.Success("$result"))
        }.catch {
            val message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
            emit(Response.Error(message = message))
            crashlytics.recordException(IllegalArgumentException(message))
        }
    }
}