package br.com.mobdhi.morinha.domain.repository

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.model.Vaccine
import kotlinx.coroutines.flow.Flow

interface VaccinesRepository {
    fun getVaccines(pet: Pet): Flow<Response<List<Vaccine>>>
    fun addVaccine(vaccine: Vaccine): Flow<Response<String>>
}