package br.com.mobdhi.morinha.domain.model

data class PetDTO(
    val name: String = "",
    val specie: Specie = Specie(),
    val breed: String = "",
    val bornDate: String = "",
    val genre: Genre = Genre(),
    val weight: String = "0.0",
    val color: String = "",
    val tutorId: String = ""
)
