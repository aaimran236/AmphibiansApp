package com.example.amphibians

import com.example.amphibians.data.NetworkAmphibianInfosRepository
import com.example.amphibians.fake.FakeDataSource
import com.example.amphibians.fake.FakeAmphibiansApiService
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NetworkAmphibianInfosRepositoryTest {

    /*
 The coroutine test library provides the runTest() function. The function takes the method
 that you passed in the lambda and runs it from TestScope, which inherits from CoroutineScope.
 */
    @Test
    fun networkAmphibianInfosRepository_getAmphibians_verifyAmphibianList()= runTest{
        val repository= NetworkAmphibianInfosRepository(
            amphibiansApiService = FakeAmphibiansApiService()
        )
        assertEquals(FakeDataSource.amphibiansList,repository.getAmphibians())
    }
}