package br.com.mobdhi.morinha.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Specie(
    val id: Int? = null,
    val name: String = ""
)
