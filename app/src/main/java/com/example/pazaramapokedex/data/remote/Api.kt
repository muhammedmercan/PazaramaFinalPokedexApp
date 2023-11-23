package com.example.pazaramapokedex.data.remote

import com.example.pazaramapokedex.domain.model.PokemonImage
import com.example.pokedex.domain.model.PokemonResponse
import com.example.pokedex.domain.model.SinglePokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {


    @GET("pokemon/")
    suspend fun getPokemons(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Response<PokemonResponse>

    @GET("pokemon/{id}/")
    suspend fun getSinglePokemon(
        @Path("id") id: Int
    ): Response<SinglePokemonResponse>

    @GET("pokemon/{id}/")
    suspend fun getPokemonImage(
        @Path("id") id: Int
    ): Response<PokemonImage>

}