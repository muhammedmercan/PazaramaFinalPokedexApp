package com.example.pazaramapokedex.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.pazaramapokedex.data.repository.FakeRepository
import com.example.pazaramapokedex.domain.use_case.GetPokemonsUseCase
import com.example.pazaramapokedex.domain.use_case.getSinglePokemonUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: FakeRepository

    private lateinit var getPokemonsUseCase: GetPokemonsUseCase
    private lateinit var getSinglePokemonUseCase: getSinglePokemonUseCase

    @Before
    fun setup() {

        repository = FakeRepository()
        viewModel = MainViewModel(
            GetPokemonsUseCase(repository),
            getSinglePokemonUseCase(repository)
        )
    }


    @Test
    fun `get pokemons return success`() = runTest {

        viewModel.getPokemons()

        viewModel.pokemonList.test {
            val uiState = awaitItem()
            assertThat(uiState.isLoading).isTrue()
            advanceUntilIdle()
            val uiState2 = awaitItem()
            assertThat(uiState2.isLoading).isFalse()
            assertThat(uiState2.pokemons).isNotEmpty()
            assertThat(uiState2.error).isNull()

        }
    }

    @Test
    fun `get pokemons return error`() = runTest {

        viewModel.getPokemons()

        viewModel.pokemonList.test {

            val uiState = awaitItem()
            assertThat(uiState.isLoading).isTrue()
            advanceUntilIdle()
            var uiState2 = awaitItem()
            uiState2 = MainState(isLoading = false, error = "hata")
            assertThat(uiState2.isLoading).isFalse()
            assertThat(uiState2.error).isNotNull()


        }
    }

        @Test
        fun `get single pokemon return success`() = runTest {

            viewModel.getSinglePokemon("2")

            viewModel.pokemonList.test {
                val uiState = awaitItem()
                assertThat(uiState.isLoading).isTrue()
                advanceUntilIdle()
                val uiState2 = awaitItem()
                assertThat(uiState2.isLoading).isFalse()
                assertThat(uiState2.pokemons).isNotEmpty()
                assertThat(uiState2.error).isNull()

            }
        }


        @Test
        fun `get single pokemon return error`() = runTest {

            viewModel.getSinglePokemon("-112112")

            viewModel.pokemonList.test {

                val uiState = awaitItem()
                assertThat(uiState.isLoading).isTrue()
                advanceUntilIdle()
                var uiState2 = awaitItem()
                uiState2 = MainState(isLoading = false, error = "hata")
                assertThat(uiState2.isLoading).isFalse()
                assertThat(uiState2.error).isNotNull()

            }
        }

}