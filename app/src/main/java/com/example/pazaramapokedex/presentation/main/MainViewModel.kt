package com.example.pazaramapokedex.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazaramapokedex.domain.repository.RepositoryInterface
import com.example.pazaramapokedex.utils.Resource
import com.example.pazaramapokedex.utils.extractId
import com.example.pokedex.domain.model.PokemonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : RepositoryInterface
) : ViewModel() {

    private var pokemons = MutableLiveData<Resource<PokemonResponse>>()
    val pokemonList : LiveData<Resource<PokemonResponse>>
        get() = pokemons


    fun getPokemons(limit : Int, offset : Int)   {

        viewModelScope.launch {

            pokemons.value = repository.getPokemons(limit,offset)

        }
    }
}