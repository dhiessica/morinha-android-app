package br.com.mobdhi.morinha.domain.repository

import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Response
import br.com.mobdhi.morinha.domain.model.User
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    /**
     * Função para listar todos os pets.
     */
    fun getPets(): Flow<Response<List<Pet>>>

    /**
     * Função para buscar um pet.
     */
    fun getPet(id: String): Flow<Response<Pet>>

    /**
     * Função para adicionar um pet
     *
     * @param pet é um objeto [Pet] com os dados do pet que será adicionado.
     */
    fun addPet(pet: Pet): Flow<Response<String>>

    /**
     * Função para atualizar os dados de um pet
     *
     * @param pet é um objeto [Pet] com os dados do pet que será atualizado.
     */
    fun updatePet(pet: Pet): Flow<Response<String>>

    /**
     * Função para deletar informações de um pet
     *
     * @param pet é um objeto [Pet] com os dados do pet que será deletado.
     */
    fun deletePet(pet: Pet): Flow<Response<String>>
}