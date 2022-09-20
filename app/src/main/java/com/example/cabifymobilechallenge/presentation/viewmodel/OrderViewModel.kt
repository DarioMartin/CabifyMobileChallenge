package com.example.cabifymobilechallenge.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.usecases.ProcessOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(val processOrderUseCase: ProcessOrderUseCase) :
    ViewModel() {

    var uiState by mutableStateOf<OrderUIState>(OrderUIState.Loading)
        private set


    init {
        processOrder()
    }

    fun processOrder() {
        viewModelScope.launch {
            uiState = when (processOrderUseCase()) {
                is Response.Error -> OrderUIState.Error
                is Response.Success -> OrderUIState.Success
            }
        }
    }
}

sealed class OrderUIState {
    object Loading : OrderUIState()
    object Error : OrderUIState()
    object Success : OrderUIState()
}