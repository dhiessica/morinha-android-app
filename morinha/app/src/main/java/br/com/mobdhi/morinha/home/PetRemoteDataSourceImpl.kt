package br.com.mobdhi.morinha.home

import br.com.mobdhi.morinha.domain.datasource.PetDataSource
import br.com.mobdhi.morinha.domain.datasource.RemoteDataBasePaths.PETS
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
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
                document.toObject(Pet::class.java)
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
}