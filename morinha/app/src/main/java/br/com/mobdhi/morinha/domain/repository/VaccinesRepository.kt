package br.com.mobdhi.morinha.domain.repository

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.model.Vaccine
import kotlinx.coroutines.flow.Flow

interface VaccinesRepository {
    /**
     * Função para listar todas as vacinas.
     */
    fun getVaccines(pet: Pet): Flow<Response<List<Vaccine>>>

    /**
     * Função para adicionar um vacina
     *
     * @param vaccine é um objeto [Vaccine] com os dados da vacina que será adicionada.
     */
    fun addVaccine(vaccine: Vaccine): Flow<Response<String>>

    /**
     * Função para atualizar os dados de uma vacina
     *
     * @param vaccine é um objeto [Vaccine] com os dados da vacina que será atualizada.
     */
    fun updateVaccine(vaccine: Vaccine): Flow<Response<String>>

    /**
     * Função para deletar os dados de uma vacina
     *
     * @param vaccine é um objeto [Vaccine] com os dados da vacina que será deletada.
     */
    fun deleteVaccine(vaccine: Vaccine): Flow<Response<String>>
}