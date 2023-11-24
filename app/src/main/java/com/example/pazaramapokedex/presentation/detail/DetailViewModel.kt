package com.example.pazaramapokedex.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazaramapokedex.domain.repository.RepositoryInterface
import com.example.pazaramapokedex.utils.Resource
import com.example.pokedex.domain.model.PokemonResponse
import com.example.pokedex.domain.model.SinglePokemonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository : RepositoryInterface

) : ViewModel() {

    private var pokemonDetails = MutableLiveData<Resource<SinglePokemonResponse>>()
    val pokemonList : LiveData<Resource<SinglePokemonResponse>>
        get() = pokemonDetails

    fun resetpokemonDetails() {
        pokemonDetails = MutableLiveData<Resource<SinglePokemonResponse>>()
    }

    fun getSinglePokemon(id: String) {

        viewModelScope.launch {

            //TODO bunun böyle yapılıp yapılmadıgına bakılacak
            pokemonDetails.value = repository.getSinglePokemon(id)

        }
    }


}