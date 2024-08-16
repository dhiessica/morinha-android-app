package br.com.mobdhi.morinha.domain.repository

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getPets(): Flow<Response<List<Pet>>>
}