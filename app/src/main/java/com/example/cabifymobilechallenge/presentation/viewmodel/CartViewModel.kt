package com.example.cabifymobilechallenge.presentation.viewmodel

import android.icu.util.Currency
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.usecases.CartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val useCases: CartUseCases) :
    ViewModel() {

    val uiState: MutableState<CartUIState> = mutableStateOf(CartUIState.Content())
    private var shoppingCart = mutableStateOf(ShoppingCart())
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
                is Response.Error -> CartUIState.Error
                is Response.Success -> {
                    shoppingCart.value =
                        shoppingCart.value.copy(products = result.data ?: emptyList())
                    CartUIState.Content(
                        shoppingCart = shoppingCart.value,
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
                is Response.Error -> CartUIState.Error
                is Response.Success -> {
                    shoppingCart.value =
                        shoppingCart.value.copy(
                            discounts = result.data?.toMutableList() ?: mutableListOf()
                        )
                    CartUIState.Content(
                        shoppingCart = shoppingCart.value,
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
                is Response.Error -> CartUIState.Error
                is Response.Success -> {
                    availableDiscounts = result.data ?: emptyList()
                    CartUIState.Content(
                        shoppingCart = shoppingCart.value,
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
                    CartUIState.Error
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
                    CartUIState.Error
                }
                is Response.Success -> {
                    if (result.data == true) loadCartDiscounts()
                }
            }
        }
    }

    fun getSubTotalPrice(): Double {
        return shoppingCart.value.products.sumOf { it.price }
    }

    fun getDiscountAmount(): Double {
        return getSubTotalPrice() - getTotalPrice()
    }

    fun getCurrency(): Currency {
        return Currency.getInstance("EUR")
    }

    fun getTotalPrice(): Double {
        return shoppingCart.value.total()
    }

}

sealed class CartUIState {
    object Loading : CartUIState()
    object Error : CartUIState()
    data class Content(
        val shoppingCart: ShoppingCart = ShoppingCart(),
        val availableDiscounts: List<Discount> = emptyList()
    ) : CartUIState()
}