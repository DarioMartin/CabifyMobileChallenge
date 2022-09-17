package com.example.cabifymobilechallenge.presentation.viewmodel

import android.icu.util.Currency
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.usecases.CartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val useCases: CartUseCases) :
    ViewModel() {

    val uiState: MutableState<CartUIState> = mutableStateOf(CartUIState.Content())

    private var cartProducts = emptyList<Product>()
    private var cartDiscounts = mutableListOf<Discount>()
    private var availableDiscounts = emptyList<Discount>()

    init {
        uiState.value = CartUIState.Loading

        loadAvailableDiscounts()
        loadCartProducts()
        loadCartDiscounts()
    }

    private fun loadCartProducts() {
        viewModelScope.launch {
            val newValue = when (val result = useCases.getCartProductsUseCase()) {
                is Response.Error -> CartUIState.Error(CartError.LoadCartProducts)
                is Response.Success -> {
                    cartProducts = result.data ?: emptyList()
                    CartUIState.Content(
                        cartProducts = cartProducts,
                        cartDiscounts = cartDiscounts,
                        availableDiscounts = availableDiscounts
                    )
                }
            }

            uiState.value = newValue
        }
    }

    private fun loadCartDiscounts() {
        viewModelScope.launch {
            val newValue = when (val result = useCases.getCartDiscountsUseCase()) {
                is Response.Error -> CartUIState.Error(CartError.LoadCartDiscounts)
                is Response.Success -> {
                    cartDiscounts = result.data?.toMutableList() ?: mutableListOf()
                    CartUIState.Content(
                        cartProducts = cartProducts,
                        cartDiscounts = cartDiscounts,
                        availableDiscounts = availableDiscounts
                    )
                }
            }

            uiState.value = newValue
        }
    }

    private fun loadAvailableDiscounts() {
        viewModelScope.launch {
            uiState.value = when (val result = useCases.getAvailableDiscountsUseCase()) {
                is Response.Error -> CartUIState.Error(CartError.AvailableDiscounts)
                is Response.Success -> {
                    availableDiscounts = result.data ?: emptyList()
                    CartUIState.Content(
                        cartProducts = cartProducts,
                        cartDiscounts = cartDiscounts,
                        availableDiscounts = availableDiscounts
                    )
                }
            }
        }
    }

    fun addDiscount(discount: Discount) {
        viewModelScope.launch {
            when (val result = useCases.addDiscountToCartUseCase(discount)) {
                is Response.Error -> {
                    CartUIState.Error(CartError.AddDiscount)
                }
                is Response.Success -> {
                    if (result.data == true) loadCartDiscounts()
                }
            }
        }
    }

    fun removeDiscount(discount: Discount) {
        viewModelScope.launch {
            when (val result = useCases.removeDiscountFromCartUseCase(discount)) {
                is Response.Error -> {
                    CartUIState.Error(CartError.RemoveDiscount)
                }
                is Response.Success -> {
                    if (result.data == true) loadCartDiscounts()
                }
            }
        }
    }

    fun isDiscountAdded(discount: Discount): Boolean {
        return cartDiscounts.contains(discount)
    }

    fun getSubTotalPrice(): Double {
        return cartProducts.sumOf { it.price }
    }

    fun getDiscountAmount(): Double {
        return getSubTotalPrice() - getTotalPrice()
    }

    fun getTotalPrice(): Double {
        return ShoppingCart(cartProducts, cartDiscounts).total()
    }

    fun getCurrency(): Currency {
        return Currency.getInstance("EUR")
    }

}

sealed class CartUIState {
    object Loading : CartUIState()
    data class Error(val error: CartError) : CartUIState()
    data class Content(
        val cartProducts: List<Product> = emptyList(),
        val cartDiscounts: List<Discount> = emptyList(),
        val availableDiscounts: List<Discount> = emptyList()
    ) : CartUIState()
}

sealed class CartError {
    object AddDiscount : CartError()
    object RemoveDiscount : CartError()
    object LoadCartProducts : CartError()
    object LoadCartDiscounts : CartError()
    object AvailableDiscounts : CartError()
}