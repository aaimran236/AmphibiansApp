package com.example.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.model.AmphibianInfo
import kotlinx.coroutines.launch


/**
 * UI state for the Home screen
 */
sealed interface AmphibianUiState {
    data class Success(val amphibians: List<AmphibianInfo>) : AmphibianUiState
    object Error : AmphibianUiState
    object Loading : AmphibianUiState
}

class AmphibiansViewModel(
// TODO: Add repository object as parameter
) : ViewModel() {
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
            amphibianUiState = AmphibianUiState.Loading
            // TODO:Get Amphibians information from repository
        }
    }
}

