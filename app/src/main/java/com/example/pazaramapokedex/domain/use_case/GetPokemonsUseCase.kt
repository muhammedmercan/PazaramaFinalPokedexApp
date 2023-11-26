package com.example.pazaramapokedex.domain.use_case

import com.example.pazaramapokedex.data.remote.dto.toPokemonBasic
import com.example.pazaramapokedex.domain.repository.RepositoryInterface
import com.example.pazaramapokedex.utils.Resource
import com.example.pokedex.domain.model.PokemonBasic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor(
    private val repository : RepositoryInterface
) {


    fun executeGetPokemons(limit : Int, offset : Int) : Flow<Resource<List<PokemonBasic>>> = flow {

        try {
            emit(Resource.Loading())
            val pokemonList = repository.getPokemons(limit, offset)
            if(pokemonList.results?.isNotEmpty()!!) {
                emit(Resource.Success(pokemonList.toPokemonBasic()!!))
            } else {
                emit(Resource.Error(message = "No movie found"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Could not reach internet"))
        }


    }


}