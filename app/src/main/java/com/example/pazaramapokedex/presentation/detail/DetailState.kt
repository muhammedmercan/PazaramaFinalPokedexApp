package com.example.pazaramapokedex.presentation.detail

import com.example.pokedex.domain.model.PokemonDetails

data class DetailState (

    val isLoading : Boolean = false,
    val details : PokemonDetails? = null,
    val error : String = ""
)