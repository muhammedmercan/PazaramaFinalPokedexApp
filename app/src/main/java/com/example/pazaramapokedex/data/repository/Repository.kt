package com.example.pazaramapokedex.data.repository

import com.example.pazaramapokedex.data.remote.Api
import com.example.pazaramapokedex.domain.model.PokemonImage
import com.example.pazaramapokedex.domain.repository.RepositoryInterface
import com.example.pazaramapokedex.utils.Resource
import com.example.pokedex.domain.model.PokemonResponse
import com.example.pokedex.domain.model.SinglePokemonResponse
import java.lang.Exception
import javax.inject.Inject


class Repository @Inject constructor(
    private val retrofitApi : Api
)
    : RepositoryInterface {


    override suspend fun getPokemons(limit : Int, offset : Int) : Resource<PokemonResponse> {

        return try {
            val response = retrofitApi.getPokemons(limit,offset)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }

    }

    override suspend fun getSinglePokemon(id : Int) : Resource<SinglePokemonResponse> {

        return try {

            val response = retrofitApi.getSinglePokemon(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }
    }

    override suspend fun getPokemonImage(id : Int) : Resource<PokemonImage> {

        return try {

            val response = retrofitApi.getPokemonImage(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }
    }



}