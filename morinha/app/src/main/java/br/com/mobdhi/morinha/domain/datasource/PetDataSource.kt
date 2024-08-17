package br.com.mobdhi.morinha.domain.datasource

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface PetDataSource {
    fun getPets(): Flow<Response<List<Pet>>>
}