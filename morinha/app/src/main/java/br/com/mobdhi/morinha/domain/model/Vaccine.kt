package br.com.mobdhi.morinha.domain.model

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
