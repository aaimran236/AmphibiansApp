package com.example.amphibians.data

import com.example.amphibians.model.AmphibianInfo
import com.example.amphibians.network.AmphibiansApi
import com.example.amphibians.network.AmphibiansApiService

interface AmphibianInfosRepository {
    suspend fun getAmphibians(): List<AmphibianInfo>
}

class NetworkAmphibianInfosRepository() : AmphibianInfosRepository {
        override suspend fun getAmphibians(): List<AmphibianInfo> {
        return AmphibiansApi.retrofitService.getAmphibians()
    }
}