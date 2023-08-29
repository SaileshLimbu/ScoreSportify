package com.reachmobi.scoresportify.data.network

import com.reachmobi.scoresportify.data.response.PlayerListResponse
import com.reachmobi.scoresportify.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ScoreEndPoints {

    @GET(Constants.URL.SEARCH_PLAYER)
    suspend fun searchPlayer(@Query("p") playerName: String): Response<PlayerListResponse>
}