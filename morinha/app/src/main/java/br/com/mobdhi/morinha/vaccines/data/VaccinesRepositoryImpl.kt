package br.com.mobdhi.morinha.vaccines.data

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.domain.repository.VaccinesRepository
import kotlinx.coroutines.flow.Flow

class VaccinesRepositoryImpl(
    private val remoteDataSourceImpl: VaccinesRemoteDataSourceImpl
) : VaccinesRepository {
    override fun getVaccines(pet: Pet): Flow<Response<List<Vaccine>>> {
        return remoteDataSourceImpl.getVaccines(pet)
    }

    override fun addVaccine(vaccine: Vaccine): Flow<Response<String>> {
        return remoteDataSourceImpl.addVaccine(vaccine)
    }
}