package com.example.pazaramapokedex.domain.repository

import com.example.pazaramapokedex.utils.Resource
import com.example.pokedex.domain.model.PokemonResponse
import com.example.pokedex.domain.model.SinglePokemonResponse

interface RepositoryInterface {

    suspend fun getPokemons(limit : Int, offset : Int) : Resource<PokemonResponse>

    suspend fun getSinglePokemon(id : Int) : Resource<SinglePokemonResponse>


}