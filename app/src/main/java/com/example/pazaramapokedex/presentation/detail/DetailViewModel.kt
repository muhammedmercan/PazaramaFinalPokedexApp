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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSinglePokemonUseCase: getSinglePokemonUseCase

) : ViewModel() {

    private var _uiState = MutableStateFlow<DetailState>(DetailState())
    val uiState : StateFlow<DetailState>
        get() = _uiState


    fun resetpokemonDetails() {
        _uiState = MutableStateFlow<DetailState>(DetailState())
    }

    fun getSinglePokemon(id: String) {

        _uiState.value = DetailState(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {

            getSinglePokemonUseCase.executeGetSinglePokemon(id).collect {

                when(it) {

                    is Resource.Success -> {

                        _uiState.value = DetailState(details = it.data,
                            isLoading = false)

                    }

                    is Resource.Error -> {
                        _uiState.value = DetailState(error = it.message ?: "Error!",
                            isLoading = false)
                    }

                    is Resource.Loading -> {
                        _uiState.value = DetailState(isLoading = true)
                    }
                }
            }
        }
    }


}