package com.example.pazaramapokedex.data.repository

import com.example.pazaramapokedex.data.remote.Api
import com.example.pazaramapokedex.data.remote.dto.PokemonBasicDto
import com.example.pazaramapokedex.data.remote.dto.PokemonDetailsDto
import com.example.pazaramapokedex.domain.repository.RepositoryInterface
import javax.inject.Inject


class Repository @Inject constructor(
    private val retrofitApi : Api
)
    : RepositoryInterface {

    override suspend fun getPokemons(limit : Int, offset : Int) : PokemonBasicDto {

        return retrofitApi.getPokemons(limit,offset)

    }

    override suspend fun getSinglePokemon(id : String) : PokemonDetailsDto {

        return retrofitApi.getSinglePokemon(id)

    }
}