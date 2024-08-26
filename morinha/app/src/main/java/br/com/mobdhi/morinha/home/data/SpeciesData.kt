package br.com.mobdhi.morinha.home.data

import br.com.mobdhi.morinha.domain.model.Genre
import br.com.mobdhi.morinha.domain.model.Specie

fun getDefaultSpecies(): List<Specie> {
    return listOf(
        Specie(
            id = 0,
            name = "Cão"
        ),
        Specie(
            id = 1,
            name = "Gato"
        )
    )
}

fun getDefaultGenres(): List<Genre> {
    return listOf(
        Genre(
            id = 0,
            name = "Fêmea"
        ),
        Genre(
            id = 1,
            name = "Macho"
        )
    )
}