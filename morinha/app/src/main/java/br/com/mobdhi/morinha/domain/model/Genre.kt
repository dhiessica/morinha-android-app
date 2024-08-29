package br.com.mobdhi.morinha.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int? = null,
    val name: String = ""
)
