package com.reachmobi.scoresportify.data.repositories

import com.reachmobi.scoresportify.data.network.ScoreEndPoints
import com.reachmobi.scoresportify.models.Player
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val endPoints: ScoreEndPoints
) : PlayerRepository {

    override suspend fun searchPlayer(playerName: String): List<Player> {
        val response = endPoints.searchPlayer(playerName)
        if (response.isSuccessful) {
            return response.body()?.playerList.takeIf { it?.isNotEmpty() == true }
                ?: throw Exception("No data.")
        } else {
            throw Exception(response.errorBody().toString())
        }
    }
}