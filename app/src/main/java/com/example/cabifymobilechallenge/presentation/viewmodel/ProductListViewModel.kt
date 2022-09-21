package com.example.cabifymobilechallenge.presentation.viewmodel

import android.icu.util.Currency
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.usecases.ProductListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    var currency: Currency,
    private val useCases: ProductListUseCases
) :
    ViewModel() {

    val uiState: MutableState<UIState> = mutableStateOf(UIState.Empty)
    val products = mutableStateListOf<Product>()

    private var _errorEvents = Channel<Unit>()
    var errorEvents = _errorEvents.receiveAsFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        uiState.value = UIState.Loading
        viewModelScope.launch {
            when (val result = useCases.getProductsUseCase()) {
                is Response.Error -> uiState.value = UIState.Error
                is Response.Success -> updateProducts(result.data)
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            when (useCases.addProductToCartUseCase(product)) {
                is Response.Error -> _errorEvents.send(Unit)
                is Response.Success -> updateProduct(product) { it.increment() }
            }
        }
    }

    fun removeProduct(product: Product) {
        viewModelScope.launch {
            when (useCases.removeProductsFromCartUseCase(product)) {
                is Response.Error -> _errorEvents.send(Unit)
                is Response.Success -> updateProduct(product) { it.decrement() }
            }
        }
    }

    private fun updateProduct(product: Product, action: (Product) -> Unit) {
        products.firstOrNull { it == product }?.let {
            val index = products.indexOf(it)
            products.remove(it)
            action(it)
            products.add(index, it)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            when (val result = useCases.clearCartUseCase()) {
                is Response.Error -> _errorEvents.send(Unit)
                is Response.Success -> updateProducts(result.data)
            }
        }
    }

    private fun updateProducts(newProducts: List<Product>?) {
        products.clear()
        products.addAll(newProducts ?: emptyList())
        uiState.value = if (products.isEmpty()) UIState.Empty else UIState.Success
    }
}


sealed class UIState {
    object Loading : UIState()
    object Error : UIState()
    object Empty : UIState()
    object Success : UIState()
}