package com.example.cabifymobilechallenge.presentation.viewmodel

import android.icu.util.Currency
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.usecases.CartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    var currency: Currency,
    private val useCases: CartUseCases
) :
    ViewModel() {

    var uiState by mutableStateOf<CartUIState>(CartUIState.Loading)
        private set

    var products = mutableStateListOf<Product>()
    var discounts = mutableStateListOf<Discount>()

    private var _updateErrorFlow = MutableSharedFlow<Unit>()
    var updateErrorFlow: SharedFlow<Unit> = _updateErrorFlow.asSharedFlow()

    init {
        this.uiState = CartUIState.Loading

        loadAvailableDiscounts()
        loadCartProducts()
    }

    private fun loadCartProducts() {
        viewModelScope.launch {
            uiState =
                when (val result = useCases.getCartProductsUseCase()) {
                    is Response.Error -> CartUIState.Error
                    is Response.Success -> {
                        products.clear()
                        products.addAll(result.data ?: emptyList())
                        CartUIState.Success
                    }
                }
        }
    }

    private fun loadAvailableDiscounts() {
        viewModelScope.launch {
            uiState = when (val result = useCases.getAvailableDiscountsUseCase()) {
                is Response.Error -> CartUIState.Error
                is Response.Success -> {
                    discounts.clear()
                    discounts.addAll(result.data ?: emptyList())
                    CartUIState.Success
                }
            }
        }
    }

    fun addDiscount(discount: Discount) {
        viewModelScope.launch {
            when (useCases.activateDiscountUseCase(discount)) {
                is Response.Error -> _updateErrorFlow.emit(Unit)
                is Response.Success -> {
                    updateDiscount(discount) { it.active = true }
                }
            }
        }
    }

    fun removeDiscount(discount: Discount) {
        viewModelScope.launch {
            when (useCases.deactivateDiscountUseCase(discount)) {
                is Response.Error -> _updateErrorFlow.emit(Unit)
                is Response.Success -> {
                    updateDiscount(discount) { it.active = false }
                }
            }
        }
    }

    private fun updateDiscount(discount: Discount, action: (Discount) -> Unit) {
        val index = discounts.indexOfFirst { it.javaClass == discount.javaClass }
        if (index >= 0) {
            val d = discounts[index]
            discounts.remove(d)
            action(d)
            this.discounts.add(index, d)
        }
    }

}

sealed class CartUIState {
    object Loading : CartUIState()
    object Error : CartUIState()
    object Success : CartUIState()
}