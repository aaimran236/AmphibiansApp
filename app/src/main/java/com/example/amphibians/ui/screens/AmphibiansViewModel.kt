package com.example.amphibians.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.AmphibianInfosRepository
import com.example.amphibians.model.AmphibianInfo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


/**
 * UI state for the Home screen
 */
sealed interface AmphibianUiState {
    data class Success( val amphibians: List<AmphibianInfo>) : AmphibianUiState

    ///data class Success(val amphibians: AmphibianInfo) : AmphibianUiState
    object Error : AmphibianUiState
    object Loading : AmphibianUiState
}

/*Android framework does not allow a ViewModel to be passed values in the constructor when
 *created,we implement a ViewModelProvider.Factory object, which lets us get around this limitation.
 */
class AmphibiansViewModel(private val amphibianInfosRepository: AmphibianInfosRepository) :
    ViewModel() {
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
            amphibianUiState = try {
                ///val amphibianInfosRepository = NetworkAmphibianInfosRepository()

                AmphibianUiState.Success(amphibianInfosRepository.getAmphibians())
            } catch (e: IOException) {
                AmphibianUiState.Error
            } catch (e: HttpException) {
                AmphibianUiState.Error
            } catch (e: Exception) { // Add a general catch-all for debugging
                AmphibianUiState.Error
            }
        }
    }

    /*
     *The Factory pattern is a creational pattern used to create objects.
     *The AmphibiansViewModel.Factory object uses the application container to retrieve the
     *amphibianInfosRepository, and then passes this repository to the ViewModel when the
     * ViewModel object is created.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            /*
            define the actual instructions for creating your ViewModel. This block gets
            executed when the factory is asked to create a new MarsViewModel.
             */
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibianInfosRepository = application.container.amphibianInfosRepository
                AmphibiansViewModel(amphibianInfosRepository = amphibianInfosRepository)
            }
        }
    }
}

