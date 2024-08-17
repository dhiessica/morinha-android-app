package br.com.mobdhi.morinha.domain.model

import java.time.LocalDate

data class Pet(
    val id: String = "",
    val name: String = "",
    val specie: String = "",
    val breed: String = "",
    val bornDate: String = "",
    val genre: String = "",
    val weight: Double = 0.0,
    val color: String = "",
    val tutorId: String = ""
)
