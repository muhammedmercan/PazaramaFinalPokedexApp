package com.example.pokedex.domain.model

import com.example.pazaramapokedex.data.remote.dto.Stat

data class PokemonDetails(
    var id: Int?,
    val img: String?,
    val stats: List<Stat>,
    val ability1: String = "",
    val ability2: String = "",
    /*
    val base_stat: Int,

    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: In*/

    val name : String,
    val height: Int,
    val weight: Int,
    val types : List<String>
) {
}