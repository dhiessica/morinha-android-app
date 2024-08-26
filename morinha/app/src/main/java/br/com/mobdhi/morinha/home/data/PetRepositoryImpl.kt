package br.com.mobdhi.morinha.home.data

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class PetRepositoryImpl(
    private val remoteDataSourceImpl: PetRemoteDataSourceImpl
) : HomeRepository {
    override fun getPets(): Flow<Response<List<Pet>>> {
        return remoteDataSourceImpl.getPets() //Todo: adicionar regras pra banco de dados local
    }
}