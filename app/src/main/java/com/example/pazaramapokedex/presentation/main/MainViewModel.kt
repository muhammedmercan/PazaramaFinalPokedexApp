package com.example.pazaramapokedex.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pazaramapokedex.domain.repository.RepositoryInterface
import com.example.pazaramapokedex.domain.use_case.GetPokemonsUseCase
import com.example.pazaramapokedex.domain.use_case.getSinglePokemonUseCase
import com.example.pazaramapokedex.presentation.detail.DetailState
import com.example.pazaramapokedex.utils.Constants.BASE_URL
import com.example.pazaramapokedex.utils.Constants.SINGLE_BASE_URL
import com.example.pazaramapokedex.utils.Resource
import com.example.pokedex.domain.model.PokemonBasic
import com.example.pokedex.domain.model.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val GetPokemonsUseCase: GetPokemonsUseCase,
    private val getSinglePokemonUseCase: getSinglePokemonUseCase
) : ViewModel() {

    private var pokemons = MutableStateFlow(MainState())
    val pokemonList : StateFlow<MainState> = pokemons.asStateFlow()



    private var pokemonDetails = MutableStateFlow<DetailState>(DetailState())
    val pokemonDetail : StateFlow<DetailState>
        get() = pokemonDetails

    private var sortChoice = MutableLiveData("number")
    val choice : LiveData<String>
        get() = sortChoice

    private var job : Job? = null


    private var currentPage = 0
    private val pageSize = 20

    fun getPokemons()   {

        val limit = pageSize
        val offset = currentPage * pageSize

        val currentData = pokemons.value.pokemons?.toMutableList() ?: mutableListOf()

        pokemons.value = MainState(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
        GetPokemonsUseCase.executeGetPokemons(limit,offset).collect() { list ->

                when(list) {

                    is Resource.Success -> {

                        currentData.addAll(list.data!!)

                        pokemons.value = MainState(pokemons = list.data ?: emptyList(),
                            isLoading = false)


                        //pokemons.value = pokemons.value.copyMainState(pokemons = currentData?.plus(list.data!!) ?: emptyList())
                        currentPage++
                    }

                    is Resource.Error -> {
                        pokemons.value = MainState(error = list.message ?: "Error!")
                    }

                    is Resource.Loading -> {
                        pokemons.value = MainState(isLoading = true)
                    }

                    else -> {}
                }
            }
        }}

    fun getSinglePokemon(id: String) {

        currentPage--

        pokemons.value = MainState(isLoading = true)


        viewModelScope.launch(Dispatchers.IO) {

            getSinglePokemonUseCase.executeGetSinglePokemon(id).collect {

                when(it) {


                    is Resource.Success -> {
                        pokemons.value = MainState(pokemons = listOf(PokemonBasic(it.data?.id!!,1,"","",it.data.name!!,SINGLE_BASE_URL + it.data.id )),
                            isLoading = false)
                    }

                    is Resource.Error -> {
                        pokemons.value = MainState(error = it.message ?: "Error!",
                            isLoading = false)
                    }

                    is Resource.Loading -> {
                        pokemons.value = MainState(isLoading = true)
                    }

                    else -> {}
                }
            }
        }
    }

    fun setChoice(choiceString :String) {

        sortChoice.value = choiceString
    }
}