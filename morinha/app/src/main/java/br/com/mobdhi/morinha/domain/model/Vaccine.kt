package br.com.mobdhi.morinha.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Vaccine(
    val id: String = "",
    val applicationDate: String = "",
    val batchNumber: String = "",
    val manufacturer: String = "",
    val name: String = "",
    val nextApplicationDate: String = "",
    val observation: String = "",
    val petId: String = "",
    val veterinarian: String = ""
)

/**
 * Função para converter o objeto [Vaccine] para o [VaccineDTO] que não possui o campo ID para não
 * duplicar o id como parte do documento do firestore
 */
fun Vaccine.toVaccineDTO(): VaccineDTO = VaccineDTO(
    applicationDate,
    batchNumber,
    manufacturer,
    name,
    nextApplicationDate,
    observation,
    petId,
    veterinarian
)
