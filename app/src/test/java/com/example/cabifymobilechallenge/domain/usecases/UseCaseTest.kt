package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.local.MockedLocalDataSource
import com.example.cabifymobilechallenge.data.remote.MockedServerDataSource
import com.example.cabifymobilechallenge.data.repository.StoreRepositoryImpl
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

abstract class UseCaseTest {

    protected lateinit var successRepository: IStoreRepository
    internal lateinit var successLocalDataSource: MockedLocalDataSource
    internal lateinit var successServerDataSource: MockedServerDataSource

    protected lateinit var errorRepository: IStoreRepository
    internal lateinit var errorLocalDataSource: MockedLocalDataSource
    internal lateinit var errorServerDataSource: MockedServerDataSource

    open fun setUp() {

        successLocalDataSource = MockedLocalDataSource()
        successServerDataSource = MockedServerDataSource()

        successRepository = StoreRepositoryImpl(
            remoteDataSource = successServerDataSource,
            localDataSource = successLocalDataSource
        )

        errorLocalDataSource = MockedLocalDataSource(success = false)
        errorServerDataSource = MockedServerDataSource(success = false)

        errorRepository = StoreRepositoryImpl(
            remoteDataSource = errorServerDataSource,
            localDataSource = errorLocalDataSource
        )
    }


}