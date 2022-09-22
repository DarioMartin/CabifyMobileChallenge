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
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
internal class ProductListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var successViewModel: ProductListViewModel
    private lateinit var errorViewModel: ProductListViewModel

    @Before
    fun setup() {
        val currency = mock(Currency::class.java)
        val successRepository =
            StoreRepositoryImpl(
                MockedServerDataSource(success = true),
                MockedLocalDataSource(success = true)
            )
        val successUseCases = ProductListUseCases(
            getAvailableProductsUseCase = GetAvailableProductsUseCase(successRepository),
            addProductToCartUseCase = AddProductToCartUseCase(successRepository),
            removeProductsFromCartUseCase = RemoveProductFromCartUseCase(successRepository),
            clearCartUseCase = ClearCartUseCase(successRepository)
        )
        successViewModel = ProductListViewModel(currency, successUseCases)

        val errorRepository =
            StoreRepositoryImpl(
                MockedServerDataSource(success = false),
                MockedLocalDataSource(success = false)
            )
        val errorUseCases = ProductListUseCases(
            getAvailableProductsUseCase = GetAvailableProductsUseCase(errorRepository),
            addProductToCartUseCase = AddProductToCartUseCase(errorRepository),
            removeProductsFromCartUseCase = RemoveProductFromCartUseCase(errorRepository),
            clearCartUseCase = ClearCartUseCase(errorRepository)
        )
        errorViewModel = ProductListViewModel(currency, errorUseCases)

    }

    @Test
    fun `test success state`() = runTest {
        successViewModel.loadProducts()
        val state = successViewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(UIState.Success::class.java)
    }

    @Test
    fun `test error state`() = runTest {
        errorViewModel.loadProducts()
        val state = errorViewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(UIState.Error::class.java)
    }

    @Test
    fun `test add product`() = runTest {
        successViewModel.loadProducts()
        val products = successViewModel.products.toList()
        val product = products.first()
        val initialCount = product.count
        successViewModel.addProduct(product)
        val finalCount = successViewModel.products.first().count
        Truth.assertThat(finalCount).isEqualTo(initialCount + 1)
    }

    @Test
    fun `test remove product`() = runTest {
        successViewModel.loadProducts()
        val products = successViewModel.products.toList()
        val product = products.first()
        val initialCount = product.count
        successViewModel.removeProduct(product)
        val finalCount = successViewModel.products.first().count
        Truth.assertThat(finalCount).isEqualTo(initialCount - 1)
    }

    @Test
    fun `test clear cart`() = runTest {
        successViewModel.loadProducts()
        successViewModel.clearCart()
        Truth.assertThat(successViewModel.products.all { it.count == 0 }).isTrue()
    }

}