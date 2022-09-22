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
import org.mockito.Mockito


@ExperimentalCoroutinesApi
internal class CartViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var successViewModel: CartViewModel
    private lateinit var errorViewModel: CartViewModel

    @Before
    fun setup() {
        val currency = Mockito.mock(Currency::class.java)
        val successRepository =
            StoreRepositoryImpl(
                MockedServerDataSource(success = true),
                MockedLocalDataSource(success = true)
            )
        val successUseCases = CartUseCases(
            getCartProductsUseCase = GetCartProductsUseCase(successRepository),
            getAvailableDiscountsUseCase = GetAvailableDiscountsUseCase(successRepository),
            activateDiscountUseCase = ActivateDiscountUseCase(successRepository),
            deactivateDiscountUseCase = DeactivateDiscountUseCase(successRepository),
            getCartDiscountsUseCase = GetCartDiscountsUseCase(successRepository)
        )
        successViewModel = CartViewModel(currency, successUseCases)

        val errorRepository =
            StoreRepositoryImpl(
                MockedServerDataSource(success = false),
                MockedLocalDataSource(success = false)
            )
        val errorUseCases = CartUseCases(
            getCartProductsUseCase = GetCartProductsUseCase(errorRepository),
            getAvailableDiscountsUseCase = GetAvailableDiscountsUseCase(errorRepository),
            activateDiscountUseCase = ActivateDiscountUseCase(errorRepository),
            deactivateDiscountUseCase = DeactivateDiscountUseCase(errorRepository),
            getCartDiscountsUseCase = GetCartDiscountsUseCase(errorRepository)
        )
        errorViewModel = CartViewModel(currency, errorUseCases)

    }

    @Test
    fun `test success state after init`() = runTest {
        val state = successViewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(CartUIState.Success::class.java)
    }

    @Test
    fun `test error state after init`() = runTest {
        val state = errorViewModel.uiState.value
        Truth.assertThat(state).isInstanceOf(CartUIState.Error::class.java)
    }

    @Test
    fun `test add discount`() = runTest {
        val discount = successViewModel.discounts.first()
        successViewModel.addDiscount(discount)
        Truth.assertThat(discount.active).isTrue()
    }

    @Test
    fun `test remove discount`() = runTest {
        val discount = successViewModel.discounts.first()
        successViewModel.removeDiscount(discount)
        Truth.assertThat(discount.active).isFalse()
    }

}