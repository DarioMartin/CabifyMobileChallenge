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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val useCases: ProductListUseCases) :
    ViewModel() {

    val uiState: MutableState<UIState> = mutableStateOf(UIState.Empty)
    private var selectedProducts = mutableStateListOf<Product>()

    private var _updateErrorFlow = MutableSharedFlow<Unit>()
    var updateErrorFlow: SharedFlow<Unit> = _updateErrorFlow.asSharedFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        uiState.value = UIState.Loading
        viewModelScope.launch {
            uiState.value = when (val result = useCases.getProductsUseCase()) {
                is Response.Error -> UIState.Error
                is Response.Success -> {
                    val products = result.data
                    if (products.isNullOrEmpty()) UIState.Empty
                    else UIState.Content(result.data)
                }
            }
        }
    }

    fun itemCount(product: Product): Int {
        return selectedProducts.count { it == product }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            when (useCases.addProductToCartUseCase(product)) {
                is Response.Error -> _updateErrorFlow.emit(Unit)
                is Response.Success -> selectedProducts.add(product)
            }
        }
    }

    fun removeProduct(product: Product) {
        viewModelScope.launch {
            when (useCases.removeProductsFromCartUseCase(product)) {
                is Response.Error -> _updateErrorFlow.emit(Unit)
                is Response.Success -> selectedProducts.remove(product)
            }
        }
    }

    fun getCurrency(): Currency {
        return Currency.getInstance("EUR")
    }

    fun isCartEmpty() = selectedProducts.isEmpty()
}


sealed class UIState {
    object Loading : UIState()
    object Error : UIState()
    object Empty : UIState()
    data class Content(val products: List<Product>) : UIState()
}