package com.reachmobi.scoresportify.data.repositories

import com.reachmobi.scoresportify.models.Player

interface PlayerRepository {

    suspend fun searchPlayer(playerName : String) : List<Player>
}