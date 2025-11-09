package com.example.amphibians

import com.example.amphibians.fake.FakeAmphibianInfosRepository
import com.example.amphibians.fake.FakeDataSource
import com.example.amphibians.rule.TestDispatcherRule
import com.example.amphibians.ui.screens.AmphibianUiState
import com.example.amphibians.ui.screens.AmphibiansViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

    /*
     *MarsViewModel calls the repository using viewModelScope.launch(). This instruction
     *launches a new coroutine under the default coroutine dispatcher, which is called the
     *[Main dispatcher]. The Main dispatcher wraps the Android UI thread. The reason for the
     *preceding error is the Android UI thread is not available in a unit test. Unit tests are
     *executed on your workstation, not an Android device or Emulator. If code under a local
     *unit test references the Main dispatcher, an exception (like the one above) is thrown
     *when the unit tests are run. To overcome this issue, you must explicitly define the
     *default dispatcher when running unit tests.
     */
    class AmphibiansViewModelTest {
        @get:Rule
        val dispatcher = TestDispatcherRule()

        @Test
        fun amphibiansViewModel_getAmphibiansInfo_verifyAmphibianUiStateSuccess()= runTest{

            val viewmodel= AmphibiansViewModel(
                amphibianInfosRepository = FakeAmphibianInfosRepository()
            )

            assertEquals(
                AmphibianUiState.Success(FakeDataSource.amphibiansList),
                viewmodel.amphibianUiState
            )
        }
    }