package br.com.mobdhi.morinha.domain.repository

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    fun getPets(): Flow<Response<List<Pet>>>
    fun addPet(pet: Pet): Flow<Response<String>>
    fun updatePet(pet: Pet): Flow<Response<String>>
    fun deletePet(pet: Pet): Flow<Response<String>>
}