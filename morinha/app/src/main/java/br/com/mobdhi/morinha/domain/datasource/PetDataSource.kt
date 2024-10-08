package br.com.mobdhi.morinha.domain.datasource

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow

interface PetDataSource {
    fun getPets(): Flow<Response<List<Pet>>>
    fun getPet(id: String): Flow<Response<Pet>>
    fun addPets(pet: Pet): Flow<Response<String>>
    fun updatePet(pet: Pet): Flow<Response<String>>
    fun deletePet(pet: Pet): Flow<Response<String>>
}