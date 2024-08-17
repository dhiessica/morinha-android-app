package br.com.mobdhi.morinha.home

import br.com.mobdhi.morinha.domain.datasource.PetDataSource
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class HomeRepositoryImpl(
    private val remoteDataSourceImpl: PetRemoteDataSourceImpl
) : HomeRepository {
    override fun getPets(): Flow<Response<List<Pet>>> {
        return remoteDataSourceImpl.getPets() //Todo: adicionar regras pra banco de dados local
    }
}