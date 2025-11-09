package com.example.amphibians.fake

import com.example.amphibians.model.AmphibianInfo
import com.example.amphibians.network.AmphibiansApiService

class FakeAmphibiansApiService : AmphibiansApiService{
    override suspend fun getAmphibians(): List<AmphibianInfo> {
       return FakeDataSource.amphibiansList
    }
}