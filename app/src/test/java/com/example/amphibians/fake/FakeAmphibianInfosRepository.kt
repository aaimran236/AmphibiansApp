package com.example.amphibians.fake

import com.example.amphibians.data.AmphibianInfosRepository
import com.example.amphibians.model.AmphibianInfo

class FakeAmphibianInfosRepository : AmphibianInfosRepository {
    override suspend fun getAmphibians(): List<AmphibianInfo> {
        return FakeDataSource.amphibiansList
    }
}