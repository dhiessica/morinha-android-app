package br.com.mobdhi.morinha.pet.data

import br.com.mobdhi.morinha.domain.RemoteDataBasePaths
import br.com.mobdhi.morinha.domain.datasource.PetDataSource
import br.com.mobdhi.morinha.domain.RemoteDataBasePaths.PETS
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.domain.model.toPetDTO
import br.com.mobdhi.morinha.domain.repository.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class PetRemoteDataSourceImpl(
    private val dataBase: FirebaseFirestore,
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
            emit(
                Response.Error(
                    message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
                )
            )
        }
    }

    override fun addPets(pet: Pet): Flow<Response<String>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(PETS)
                .add(pet.copy(tutorId = auth.currentUserId).toPetDTO()).await()

            emit(Response.Success(data = result.id))
        }.catch {
            emit(
                Response.Error(
                    message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
                )
            )
        }
    }

    override fun updatePet(pet: Pet): Flow<Response<String>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(PETS).document(pet.id).set(pet).await()

            emit(Response.Success(data = "$result"))
        }.catch {
            emit(
                Response.Error(
                    message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
                )
            )
        }
    }

    override fun deletePet(pet: Pet): Flow<Response<String>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(PETS).document(pet.id).delete().await()

            emit(Response.Success(data = "$result"))
        }.catch {
            emit(
                Response.Error(
                    message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
                )
            )
        }
    }
}