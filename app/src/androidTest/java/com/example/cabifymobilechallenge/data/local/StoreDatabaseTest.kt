package com.example.cabifymobilechallenge.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.cabifymobilechallenge.data.local.dao.DiscountDao
import com.example.cabifymobilechallenge.data.local.dao.ProductDao
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException


@ExperimentalCoroutinesApi
internal class StoreDatabaseTest {

    private lateinit var productDao: ProductDao
    private lateinit var discountDao: DiscountDao
    private lateinit var db: StoreDatabase

    private lateinit var voucher: Product
    private lateinit var mug: Product
    private lateinit var tShirt: Product

    private lateinit var twoXOneVoucher: Discount
    private lateinit var tShirtBulk: Discount

    @Before
    fun createDb() {

        voucher = Product.Voucher(name = "Test Voucher", price = 10.0)
        mug = Product.Mug(name = "Test Mug", price = 2.0)
        tShirt = Product.TShirt(name = "Test Tshirt", price = 5.0)

        twoXOneVoucher = Discount.TwoPer1VoucherPromo
        tShirtBulk = Discount.TShirtBulkPromo


        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, StoreDatabase::class.java
        ).build()
        productDao = db.getProductDao()
        discountDao = db.getDiscountDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    private suspend fun fillDataBase() {
        productDao.insertAll(List(3) { voucher.toEntity() })
        productDao.insertAll(List(1) { mug.toEntity() })
        productDao.insertAll(List(5) { tShirt.toEntity() })

        discountDao.insertAll(listOf(twoXOneVoucher.toEntity(), tShirtBulk.toEntity()))
    }

    @Test
    @Throws(Exception::class)
    fun testInsertProducts() = runTest {
        fillDataBase()
        val count = productDao.getAllProducts().size
        assertThat(count).isEqualTo(9)
    }

    @Test
    @Throws(Exception::class)
    fun testCountProduct() = runTest {
        fillDataBase()
        val count = productDao.countProductsByType(tShirt.toEntity().type)
        assertThat(count).isEqualTo(5)
    }

    @Test
    @Throws(Exception::class)
    fun testDeleteProduct() = runTest {
        fillDataBase()
        productDao.delete(tShirt.toEntity().type)
        val count = productDao.getAllProducts().size
        assertThat(count).isEqualTo(8)
    }

    @Test
    @Throws(Exception::class)
    fun testClearProducts() = runTest {
        fillDataBase()
        productDao.clearAll()
        val count = productDao.getAllProducts().size
        assertThat(count).isEqualTo(0)
    }

    @Test
    @Throws(Exception::class)
    fun testInsertDiscounts() = runTest {
        fillDataBase()
        val count = discountDao.getDiscounts().size
        assertThat(count).isEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateDiscount() = runTest {
        fillDataBase()
        discountDao.update(twoXOneVoucher.apply { active = false }.toEntity())
        var discount =
            discountDao.getDiscounts().map { it.toDomain() }
                .filterIsInstance<Discount.TwoPer1VoucherPromo>().first()
        assertThat(discount.active).isFalse()

        discountDao.update(twoXOneVoucher.apply { active = true }.toEntity())
        discount =
            discountDao.getDiscounts().map { it.toDomain() }
                .filterIsInstance<Discount.TwoPer1VoucherPromo>().first()
        assertThat(discount.active).isTrue()
    }

    @Test
    @Throws(Exception::class)
    fun testClearDiscounts() = runTest {
        fillDataBase()
        discountDao.clearAll()
        val count = discountDao.getDiscounts().size
        assertThat(count).isEqualTo(0)
    }

}