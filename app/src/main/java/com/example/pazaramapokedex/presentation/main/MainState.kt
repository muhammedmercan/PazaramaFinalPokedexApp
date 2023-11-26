package com.example.pazaramapokedex.presentation.main

import com.example.pokedex.domain.model.PokemonBasic

data class MainState (
    val isLoading : Boolean = false,
    val pokemons : List<PokemonBasic>? = emptyList(),
    val error : String? = null
)