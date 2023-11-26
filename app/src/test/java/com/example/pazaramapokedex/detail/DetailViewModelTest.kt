package com.example.pazaramapokedex.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.pazaramapokedex.data.repository.FakeRepository
import com.example.pazaramapokedex.domain.use_case.GetPokemonsUseCase
import com.example.pazaramapokedex.domain.use_case.getSinglePokemonUseCase
import com.example.pazaramapokedex.presentation.detail.DetailState
import com.example.pazaramapokedex.presentation.detail.DetailViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel
    private lateinit var repository: FakeRepository

    private lateinit var getSinglePokemonUseCase: getSinglePokemonUseCase

    @Before
    fun setup() {

        repository = FakeRepository()
        viewModel = DetailViewModel(
            getSinglePokemonUseCase(repository)
        )
    }

    @Test
    fun `get single pokemon return success`() = runTest {

        viewModel.getSinglePokemon("2")

        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.isLoading).isTrue()
            advanceUntilIdle()
            val uiState2 = awaitItem()
            assertThat(uiState2.isLoading).isFalse()
            assertThat(uiState2.details).isNotNull()
            assertThat(uiState2.error).isNull()
        }
    }

    @Test
    fun `get single pokemon return error`() = runTest {

        viewModel.getSinglePokemon("-112112")

        viewModel.uiState.test {

            val uiState = awaitItem()
            assertThat(uiState.isLoading).isTrue()
            advanceUntilIdle()
            var uiState2 = awaitItem()
            uiState2 = DetailState(isLoading = false, error = "hata")
            assertThat(uiState2.isLoading).isFalse()
            assertThat(uiState2.error).isNotNull()

        }
    }

}