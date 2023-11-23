package com.example.pazaramapokedex.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.pazaramapokedex.domain.model.PokemonImage
import com.example.pazaramapokedex.domain.model.PokemonItemList
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

    private var images = MutableLiveData<List<Resource<PokemonImage>>>()
    val imageList : LiveData<List<Resource<PokemonImage>>>
        get() = images

    private var pokemonItem = MutableLiveData<List<PokemonItemList>>()
    val pokemonItemList : LiveData<List<PokemonItemList>>
        get() = pokemonItem


    fun setPokemonItemData() {

        val images2 = images.value?.map { resource ->  resource.data?.sprites?.other?.officialArtwork?.frontDefault }

        val pokemons2 = pokemons.value?.data?.results?.map { data ->  data }

        //val list  = pokemons2?.zip(images2) { pokemon, image -> PokemonItemList(pokemon?.url?.extractId()!!,pokemon.name!!,image!!)

        pokemonItem.value = pokemons2?.mapIndexed { index, data ->

            PokemonItemList(data?.url?.extractId()!!, data.name!!, images2?.get(index)!!)

        }

    }

    fun getPokemons(limit : Int, offset : Int)   {

        viewModelScope.launch {

            pokemons.value = repository.getPokemons(limit,offset)

            }
        }

    fun getPokemonImage(id:Int) {

        viewModelScope.launch {

            val data = repository.getPokemonImage(id)

            //val currentData = images.value ?: MutableLiveData<List<Resource<PokemonImage>>>()
            val currentData = images.value.orEmpty().toMutableList()
            currentData.add(data)

            images.value = currentData

        }

    }
}