package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
internal class GetCartDiscountsUseCaseTest : UseCaseTest() {

    private lateinit var successUseCase: GetCartDiscountsUseCase
    private lateinit var errorUseCase: GetCartDiscountsUseCase

    @Before
    override fun setUp() {
        super.setUp()
        successUseCase = GetCartDiscountsUseCase(successRepository)
        errorUseCase = GetCartDiscountsUseCase(errorRepository)
    }

    @Test
    fun `Test get cart discounts success`() = runTest {
        val discounts = successLocalDataSource.discounts.toList()
        val response = successUseCase.invoke()
        Truth.assertThat(response.data).isEqualTo(discounts)
    }

    @Test
    fun `Test get cart discounts error`() = runTest {
        val response = errorUseCase.invoke()
        Truth.assertThat(response).isInstanceOf(Response.Error::class.java)
    }

}