package br.com.mobdhi.morinha.pet.data

import br.com.mobdhi.morinha.domain.datasource.PetDataSource
import br.com.mobdhi.morinha.domain.RemoteDataBasePaths.PETS
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.model.toPetDTO
import br.com.mobdhi.morinha.domain.repository.AuthRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

/**
 * Funções para manipular dados dos pets no Firestore
 *
 * @param dataBase é um objeto [FirebaseFirestore] que é responsável por manipular os dados remotos.
 * @param auth é um objeto [AuthRepository] que é possue as funções de autenticação.
 */
class PetRemoteDataSourceImpl(
    private val dataBase: FirebaseFirestore,
    private val crashlytics: FirebaseCrashlytics,
    private val auth: AuthRepository
) : PetDataSource {

    override fun getPets(): Flow<Response<List<Pet>>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(PETS)
                .whereEqualTo("tutorId", auth.currentUserId).get().await()

            val pets = result.documents.mapNotNull { document ->
                document.toObject(Pet::class.java)?.copy(id = document.id)
            }

            emit(Response.Success(data = pets))
        }.catch {
            val message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
            emit(Response.Error(message = message))
            crashlytics.recordException(IllegalArgumentException(message))
        }
    }

    override fun getPet(id: String): Flow<Response<Pet>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(PETS).document(id).get().await()

            val pet = result.toObject(Pet::class.java)?.copy(id = result.id) ?: Pet()

            emit(Response.Success(data = pet))
        }.catch {
            val message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
            emit(Response.Error(message = message))
            crashlytics.recordException(IllegalArgumentException(message))
        }
    }

    override fun addPets(pet: Pet): Flow<Response<String>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(PETS)
                .add(pet.copy(tutorId = auth.currentUserId).toPetDTO()).await()

            emit(Response.Success(data = result.id))
        }.catch {
            val message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
            emit(Response.Error(message = message))
            crashlytics.recordException(IllegalArgumentException(message))
        }
    }

    override fun updatePet(pet: Pet): Flow<Response<String>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(PETS).document(pet.id).set(pet).await()

            emit(Response.Success(data = "$result"))
        }.catch {
            val message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
            emit(Response.Error(message = message))
            crashlytics.recordException(IllegalArgumentException(message))
        }
    }

    override fun deletePet(pet: Pet): Flow<Response<String>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(PETS).document(pet.id).delete().await()

            emit(Response.Success(data = "$result"))
        }.catch {
            val message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
            emit(Response.Error(message = message))
            crashlytics.recordException(IllegalArgumentException(message))
        }
    }
}