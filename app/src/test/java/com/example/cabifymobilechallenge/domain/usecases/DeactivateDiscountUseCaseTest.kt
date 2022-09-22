package com.example.cabifymobilechallenge.domain.usecases

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class DeactivateDiscountUseCaseTest : UseCaseTest() {

    private lateinit var successUseCase: DeactivateDiscountUseCase
    private lateinit var errorUseCase: DeactivateDiscountUseCase

    @Before
    override fun setUp() {
        super.setUp()
        successUseCase = DeactivateDiscountUseCase(successRepository)
        errorUseCase = DeactivateDiscountUseCase(errorRepository)
    }

    @Test
    fun `Test deactivate discount success`() = runTest {
        val discount = successLocalDataSource.getDiscountList().first()
        successUseCase.invoke(discount)
        val updatedDiscount = successLocalDataSource.getDiscountList().first()
        Truth.assertThat(updatedDiscount.active).isFalse()
    }

    @Test
    fun `Test deactivate discount error`() = runTest {
        val discount = errorLocalDataSource.getDiscountList().first()
        errorUseCase.invoke(discount)
        val updatedDiscount = errorLocalDataSource.getDiscountList().first()
        Truth.assertThat(updatedDiscount.active).isEqualTo(discount.active)
    }

}