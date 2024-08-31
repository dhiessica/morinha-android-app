package br.com.mobdhi.morinha.pet.data

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.repository.PetRepository
import kotlinx.coroutines.flow.Flow

class PetRepositoryImpl(
    private val remoteDataSourceImpl: PetRemoteDataSourceImpl
) : PetRepository {
    override fun getPets(): Flow<Response<List<Pet>>> {
        return remoteDataSourceImpl.getPets() //Todo: adicionar regras pra banco de dados local
    }

    override fun getPet(id: String): Flow<Response<Pet>> {
        return remoteDataSourceImpl.getPet(id)
    }

    override fun addPet(pet: Pet): Flow<Response<String>> {
        return remoteDataSourceImpl.addPets(pet)
    }

    override fun updatePet(pet: Pet): Flow<Response<String>> {
        return remoteDataSourceImpl.updatePet(pet)
    }

    override fun deletePet(pet: Pet): Flow<Response<String>> {
        return remoteDataSourceImpl.deletePet(pet)
    }
}