package com.example.pazaramapokedex.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazaramapokedex.domain.repository.RepositoryInterface
import com.example.pazaramapokedex.utils.Resource
import com.example.pazaramapokedex.utils.extractId
import com.example.pokedex.domain.model.PokemonResponse
import com.example.pokedex.domain.model.SinglePokemonResponse
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


    private var pokemonDetails = MutableLiveData<Resource<SinglePokemonResponse>>()
    val pokemonDetailList : LiveData<Resource<SinglePokemonResponse>>
        get() = pokemonDetails


    private var sortChoice = MutableLiveData("number")
    val choice : LiveData<String>
        get() = sortChoice


    fun getPokemons(limit : Int, offset : Int)   {

        viewModelScope.launch {

            pokemons.value = repository.getPokemons(limit,offset)

        }
    }

    fun getSinglePokemon(id: String) {

        viewModelScope.launch {

            var response = repository.getSinglePokemon(id)
            //TODO bunun böyle yapılıp yapılmadıgına bakılacak
            pokemonDetails.value = response

        }
    }

    fun setChoice(choiceString :String) {

        sortChoice.value = choiceString
    }
}