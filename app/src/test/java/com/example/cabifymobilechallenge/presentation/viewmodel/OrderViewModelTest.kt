package com.example.cabifymobilechallenge.presentation.viewmodel

import android.icu.util.Currency
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cabifymobilechallenge.MainCoroutineRule
import com.example.cabifymobilechallenge.data.local.MockedLocalDataSource
import com.example.cabifymobilechallenge.data.remote.MockedServerDataSource
import com.example.cabifymobilechallenge.data.repository.StoreRepositoryImpl
import com.example.cabifymobilechallenge.domain.usecases.*
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class OrderViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var successViewModel: OrderViewModel
    private lateinit var errorViewModel: OrderViewModel

    @Before
    fun setup() {
        val successRepository =
            StoreRepositoryImpl(
                MockedServerDataSource(success = true),
                MockedLocalDataSource(success = true)
            )
        val successUseCase = ProcessOrderUseCase(successRepository)
        successViewModel = OrderViewModel(successUseCase)

        val errorRepository =
            StoreRepositoryImpl(
                MockedServerDataSource(success = false),
                MockedLocalDataSource(success = false)
            )
        val errorUseCase = ProcessOrderUseCase(errorRepository)
        errorViewModel = OrderViewModel(errorUseCase)

    }

    @Test
    fun `test success state after init`() = runTest {
        val state = successViewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(OrderUIState.Success::class.java)
    }

    @Test
    fun `test error state after init`() = runTest {
        val state = errorViewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(OrderUIState.Error::class.java)
    }

    @Test
    fun `test success process order`() = runTest {
        successViewModel.processOrder()
        val state = successViewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(OrderUIState.Success::class.java)
    }

    @Test
    fun `test error process order`() = runTest {
        errorViewModel.processOrder()
        val state = errorViewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(OrderUIState.Error::class.java)
    }

}