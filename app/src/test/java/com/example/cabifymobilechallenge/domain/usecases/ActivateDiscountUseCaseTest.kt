package com.example.cabifymobilechallenge.domain.usecases

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ActivateDiscountUseCaseTest : UseCaseTest() {

    private lateinit var successUseCase: ActivateDiscountUseCase
    private lateinit var errorUseCase: ActivateDiscountUseCase

    @Before
    override fun setUp() {
        super.setUp()
        successUseCase = ActivateDiscountUseCase(successRepository)
        errorUseCase = ActivateDiscountUseCase(errorRepository)
    }

    @Test
    fun `Test it activates discount success`() = runTest {
        val discount = successLocalDataSource.getDiscountList().first()
        successUseCase.invoke(discount)
        val updatedDiscount = successLocalDataSource.getDiscountList().first()
        Truth.assertThat(updatedDiscount.active).isTrue()
    }

    @Test
    fun `Test it activates discount error`() = runTest {
        val discount = errorLocalDataSource.getDiscountList().first()
        errorUseCase.invoke(discount)
        val updatedDiscount = errorLocalDataSource.getDiscountList().first()
        Truth.assertThat(updatedDiscount.active).isEqualTo(discount.active)
    }

}