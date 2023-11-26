package com.example.pazaramapokedex.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazaramapokedex.domain.repository.RepositoryInterface
import com.example.pazaramapokedex.domain.use_case.GetPokemonsUseCase
import com.example.pazaramapokedex.domain.use_case.getSinglePokemonUseCase
import com.example.pazaramapokedex.presentation.main.MainState
import com.example.pazaramapokedex.utils.Resource
import com.example.pokedex.domain.model.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSinglePokemonUseCase: getSinglePokemonUseCase

) : ViewModel() {

    private var pokemonDetails = MutableStateFlow<DetailState>(DetailState())
    val pokemonDetail : StateFlow<DetailState>
        get() = pokemonDetails

    fun resetpokemonDetails() {
        pokemonDetails = MutableStateFlow<DetailState>(DetailState())
    }

    fun getSinglePokemon(id: String) {

        viewModelScope.launch {

            getSinglePokemonUseCase.executeGetSinglePokemon(id).collect {

                when(it) {

                    is Resource.Success -> {

                        pokemonDetails.value = DetailState(details = it.data)

                    }

                    is Resource.Error -> {
                        pokemonDetails.value = DetailState(error = it.message ?: "Error!")
                    }

                    is Resource.Loading -> {
                        pokemonDetails.value = DetailState(isLoading = true)
                    }
                }
            }
        }
    }


}