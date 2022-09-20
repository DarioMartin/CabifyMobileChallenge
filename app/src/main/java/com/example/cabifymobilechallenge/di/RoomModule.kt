package com.example.cabifymobilechallenge.di

import android.content.Context
import androidx.room.Room
import com.example.cabifymobilechallenge.data.local.LocalDataSource
import com.example.cabifymobilechallenge.data.local.StoreDatabase
import com.example.cabifymobilechallenge.data.local.dao.ProductDao
import com.example.cabifymobilechallenge.data.repository.ILocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val STORE_DB_NAME = "store_db"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, StoreDatabase::class.java, STORE_DB_NAME).build()

    @Singleton
    @Provides
    fun provideProductDao(db: StoreDatabase) = db.getProductDao()

    @Provides
    @Singleton
    fun providesLocalDataSource(productDao: ProductDao): ILocalDataSource {
        return LocalDataSource(productDao)
    }


}