package com.example.pazaramapokedex.data.remote

import com.example.pazaramapokedex.data.remote.dto.PokemonBasicDto
import com.example.pazaramapokedex.data.remote.dto.PokemonDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {


    @GET("pokemon/")
    suspend fun getPokemons(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): PokemonBasicDto

    @GET("pokemon/{id}/")
    suspend fun getSinglePokemon(
        @Path("id") id: String
    ): PokemonDetailsDto


}