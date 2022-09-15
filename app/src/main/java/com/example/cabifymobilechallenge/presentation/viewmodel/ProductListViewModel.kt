package com.example.cabifymobilechallenge.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.usecases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) :
    ViewModel() {

    var uiState: MutableState<UIState> = mutableStateOf(UIState.Empty)
    private var selectedProducts = mutableStateListOf<Product>()

    init {
        loadProducts()
    }

    private fun loadProducts() {

        uiState.value = UIState.Loading

        viewModelScope.launch {
            uiState.value = when (val result = getProductsUseCase()) {
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
        return selectedProducts.count { it.javaClass == product.javaClass }
    }

    fun addProduct(product: Product) {
        selectedProducts.add(product)
    }

    fun removeProduct(product: Product) {
        selectedProducts.remove(product)
    }
}


sealed class UIState {
    object Loading : UIState()
    object Error : UIState()
    object Empty : UIState()
    data class Content(val products: List<Product>) : UIState()
}