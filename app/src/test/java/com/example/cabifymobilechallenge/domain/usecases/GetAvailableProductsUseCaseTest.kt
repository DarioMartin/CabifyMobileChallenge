package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
internal class GetAvailableProductsUseCaseTest : UseCaseTest(){

    private lateinit var successUseCase: GetAvailableProductsUseCase
    private lateinit var errorUseCase: GetAvailableProductsUseCase

    @Before
    override fun setUp() {
        super.setUp()
        successUseCase = GetAvailableProductsUseCase(successRepository)
        errorUseCase = GetAvailableProductsUseCase(errorRepository)
    }

    @Test
    fun `Test get available products success`() = runTest {
        val products = successServerDataSource.getProductList()
        val response = successUseCase.invoke()
        Truth.assertThat(response.data?.size).isEqualTo(products.size)
    }

    @Test
    fun `Test get available products error`() = runTest {
        val response = errorUseCase.invoke()
        Truth.assertThat(response).isInstanceOf(Response.Error::class.java)
    }

}