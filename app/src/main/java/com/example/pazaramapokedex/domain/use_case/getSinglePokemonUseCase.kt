package com.example.pazaramapokedex.domain.use_case

import com.example.pazaramapokedex.data.remote.dto.toPokemonBasic
import com.example.pazaramapokedex.data.remote.dto.toPokemonDetail
import com.example.pazaramapokedex.domain.repository.RepositoryInterface
import com.example.pazaramapokedex.utils.Resource
import com.example.pokedex.domain.model.PokemonBasic
import com.example.pokedex.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class getSinglePokemonUseCase @Inject constructor(
    private val repository : RepositoryInterface
) {

    fun executeGetSinglePokemon(id: String) : Flow<Resource<PokemonDetails>> = flow {

        try {
            emit(Resource.Loading())
            val pokemonDetail = repository.getSinglePokemon(id = id).toPokemonDetail()
            emit(Resource.Success(pokemonDetail))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Could not reach internet"))
        }



    }
}