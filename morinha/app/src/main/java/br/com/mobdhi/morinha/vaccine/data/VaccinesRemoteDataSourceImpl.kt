package br.com.mobdhi.morinha.vaccine.data

import br.com.mobdhi.morinha.domain.RemoteDataBasePaths.VACCINES
import br.com.mobdhi.morinha.domain.datasource.VaccinesDataSource
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.domain.model.toVaccineDTO
import br.com.mobdhi.morinha.domain.repository.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class VaccinesRemoteDataSourceImpl(
    private val dataBase: FirebaseFirestore,
    private val auth: AuthRepository
): VaccinesDataSource {
    override fun getVaccines(pet: Pet): Flow<Response<List<Vaccine>>> {
        return flow {
            emit(Response.Loading())

            if (pet.tutorId == auth.currentUserId) {

                val result = dataBase.collection(VACCINES)
                    .whereEqualTo("petId", pet.id).get().await()

                val vaccines = result.documents.mapNotNull { document ->
                    document.toObject(Vaccine::class.java)?.copy(id = document.id)
                }

                emit(Response.Success(data = vaccines))

            } else throw IllegalAccessError()
        }.catch {
            emit(
                Response.Error(
                    message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
                )
            )
        }
    }

    override fun addVaccine(vaccine: Vaccine): Flow<Response<String>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(VACCINES).add(vaccine.toVaccineDTO()).await()

            emit(Response.Success(data = result.id))
        }.catch {
            emit(
                Response.Error(
                    message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
                )
            )
        }
    }

    override fun updateVaccine(vaccine: Vaccine): Flow<Response<String>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(VACCINES).document(vaccine.id).set(vaccine).await()

            emit(Response.Success(data = "$result"))
        }.catch {
            emit(
                Response.Error(
                    message = "Error ${it.message} \n Cause ${it.cause} \n Stack ${it.stackTrace}"
                )
            )
        }
    }

    override fun deleteVaccine(vaccine: Vaccine): Flow<Response<String>> {
        return flow {
            emit(Response.Loading())

            val result = dataBase.collection(VACCINES).document(vaccine.id).delete().await()

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