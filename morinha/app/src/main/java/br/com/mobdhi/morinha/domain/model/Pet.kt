package br.com.mobdhi.morinha.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Pet(
    val id: String = "",
    val name: String = "",
    val specie: Specie = Specie(),
    val breed: String = "",
    val bornDate: String = "",
    val genre: Genre = Genre(),
    val weight: String = "0.0",
    val color: String = "",
    val tutorId: String = ""
)

/**
 * Função para converter o objeto [PET] para o [PetDTO] que não possui o campo ID para não
 * duplicar o id como parte do documento do firestore
 */
fun Pet.toPetDTO(): PetDTO = PetDTO(
    name,
    specie,
    breed,
    bornDate,
    genre,
    weight,
    color,
    tutorId
)
