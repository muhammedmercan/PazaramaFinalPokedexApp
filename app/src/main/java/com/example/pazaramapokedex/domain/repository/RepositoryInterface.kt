package com.example.pazaramapokedex.domain.repository

import com.example.pazaramapokedex.data.remote.dto.PokemonBasicDto
import com.example.pazaramapokedex.data.remote.dto.PokemonDetailsDto

interface RepositoryInterface {

    suspend fun getPokemons(limit : Int, offset : Int) : PokemonBasicDto

    suspend fun getSinglePokemon(id : String) : PokemonDetailsDto


}