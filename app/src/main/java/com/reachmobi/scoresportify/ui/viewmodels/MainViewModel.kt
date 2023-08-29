package com.reachmobi.scoresportify.ui.viewmodels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reachmobi.scoresportify.data.repositories.PlayerRepository
import com.reachmobi.scoresportify.logger.Logger
import com.reachmobi.scoresportify.models.Player
import com.reachmobi.scoresportify.utils.NetworkConnectivityHelper
import com.reachmobi.scoresportify.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: PlayerRepository,
    private val logger: Logger,
    private val networkHelper: NetworkConnectivityHelper
) : ViewModel() {

    private var _playerList = MutableLiveData<Response<List<Player>>>()
    val playerList: LiveData<Response<List<Player>>>
        get() = _playerList

    fun searchPlayer(playerName: String) {
        if (!networkHelper.isNetworkAvailable()) {
            handleError(IOException("No internet connection"), playerName)
            return
        }
        logSearchEvent(playerName)
        fetchPlayer(playerName)
    }

    private fun logSearchEvent(playerName: String) {
        val params = Bundle().apply {
            putString("name", playerName)
        }
        logger.logEvent("search_player", params)
    }

    private fun fetchPlayer(playerName: String) {
        _playerList.postValue(Response.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val players = repo.searchPlayer(playerName)
                _playerList.postValue(Response.Success(players))
            } catch (ex: Exception) {
                handleError(ex, playerName)
            }
        }
    }

    private fun handleError(ex: Exception, playerName: String) {
        val errorMessage = when (ex) {
            is SocketTimeoutException -> "Server is taking too long to respond."
            is IOException -> "Network error: Please check your internet connection."
            is HttpException -> "Server returned ${ex.code()}: ${ex.message()}"
            is IllegalStateException -> "IllegalStateException: ${ex.localizedMessage}"
            else -> "An error occurred."
        }
        logErrorEvent(playerName, errorMessage)
        _playerList.postValue(Response.Error(errorMessage))
    }

    private fun logErrorEvent(playerName: String, errorMessage: String) {
        val params = Bundle().apply {
            putString("name", playerName)
            putString("error", errorMessage)
        }
        logger.logEvent("search_player_error", params)
    }

    fun logAdsEvent(eventName: String) {
        logger.logEvent(eventName, Bundle())
    }

}