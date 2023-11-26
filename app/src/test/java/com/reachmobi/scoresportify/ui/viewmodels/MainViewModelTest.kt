package com.reachmobi.scoresportify.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.reachmobi.scoresportify.data.repositories.PlayerRepository
import com.reachmobi.scoresportify.logger.Logger
import com.reachmobi.scoresportify.models.Player
import com.reachmobi.scoresportify.utils.NetworkConnectivityHelper
import com.reachmobi.scoresportify.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MainViewModelTest {

    //For Testing LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //For Coroutine
    private val dispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var playerRepository: PlayerRepository

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var networkConnectivityHelper: NetworkConnectivityHelper

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel(playerRepository, logger, networkConnectivityHelper)
    }

    @After
    fun tearDown() {
        // do any cleanup if needed
    }

    @Test
    fun `searchPlayer with no network should handle error`() = runTest {
        // Arrange
        `when`(networkConnectivityHelper.isNetworkAvailable()).thenReturn(false)
        val playerName = "Lionel Messi"

        // Act
        mainViewModel.searchPlayer(playerName)

        // Assert
        val observedValue = mainViewModel.playerList.value
        assert(observedValue is Response.Error)
    }

    @Test
    fun `fetchPlayer should post success response`() = runBlocking {
        // Arrange
        `when`(networkConnectivityHelper.isNetworkAvailable()).thenReturn(true)
        `when`(playerRepository.searchPlayer(anyString())).thenReturn(listOf(Player()))

        // Act
        mainViewModel.searchPlayer("Lionel Messi")

        //Wait coroutine to be finish
        dispatcher.scheduler.advanceUntilIdle()

        // Assert
        val observedValue = mainViewModel.playerList.value

        assert(observedValue is Response.Success) {
            "Expected Response.Success, but got $observedValue"
        }
    }
}
