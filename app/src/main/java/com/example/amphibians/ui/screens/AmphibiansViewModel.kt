package com.example.amphibians.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.data.NetworkAmphibianInfosRepository
import com.example.amphibians.model.AmphibianInfo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


/**
 * UI state for the Home screen
 */
sealed interface AmphibianUiState {
    data class Success(val amphibians: List<AmphibianInfo>) : AmphibianUiState
    ///data class Success(val amphibians: AmphibianInfo) : AmphibianUiState
    object Error : AmphibianUiState
    object Loading : AmphibianUiState
}

class AmphibiansViewModel
// TODO: Add repository object as parameter
 : ViewModel() {
    var amphibianUiState: AmphibianUiState by mutableStateOf(value = AmphibianUiState.Loading)
        private set

    /**
     * Call getAmphibiansInfo() on init so we can display status immediately.
     */
    init {
        getAmphibiansInfo()
    }

    fun getAmphibiansInfo() {
        viewModelScope.launch {
            Log.d("AmphibiansViewModel", "Attempting to fetch amphibian info...")
            amphibianUiState = AmphibianUiState.Loading
            amphibianUiState = try {
                val amphibianInfosRepository = NetworkAmphibianInfosRepository()
                Log.d("AmphibiansViewModel", "SUCCESS: Fetched amphibians.")
                AmphibianUiState.Success(amphibianInfosRepository.getAmphibians())
            } catch (e: IOException) {
                Log.e("AmphibiansViewModel", "Network I/O Error: ${e.message}", e)
                AmphibianUiState.Error
            } catch (e: HttpException) {
                Log.e("AmphibiansViewModel", "HTTP Error: ${e.message}", e)
                AmphibianUiState.Error
            } catch (e: Exception) { // Add a general catch-all for debugging
                Log.e("AmphibiansViewModel", "An unexpected error occurred: ${e.message}", e)
                AmphibianUiState.Error
            }
        }
    }
}

