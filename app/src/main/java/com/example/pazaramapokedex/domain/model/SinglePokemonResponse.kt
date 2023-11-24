package com.example.pokedex.domain.model

import com.example.pazaramapokedex.domain.model.Type

data class SinglePokemonResponse(
    var id: Int,
    val sprites: Sprites,
    val stats: List<Stats>,
    val name : String,
    val height: Int,
    val weight: Int,
    val types : List<Type>
) {
}