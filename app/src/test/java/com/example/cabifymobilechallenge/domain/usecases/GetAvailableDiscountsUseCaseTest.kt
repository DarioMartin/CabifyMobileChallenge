package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class GetAvailableDiscountsUseCaseTest : UseCaseTest() {

    private lateinit var successUseCase: GetAvailableDiscountsUseCase
    private lateinit var errorUseCase: GetAvailableDiscountsUseCase

    @Before
    override fun setUp() {
        super.setUp()
        successUseCase = GetAvailableDiscountsUseCase(successRepository)
        errorUseCase = GetAvailableDiscountsUseCase(errorRepository)
    }

    @Test
    fun `Test get discounts success`() = runTest {
        val response = successUseCase.invoke()
        Truth.assertThat(response.data).isEqualTo(successServerDataSource.getDiscountList())
    }

    @Test
    fun `Test get discounts error`() = runTest {
        val response = errorUseCase.invoke()
        Truth.assertThat(response).isInstanceOf(Response.Error::class.java)
    }

}