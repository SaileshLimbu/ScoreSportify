package com.reachmobi.scoresportify.data.response

import com.google.gson.annotations.SerializedName
import com.reachmobi.scoresportify.models.Player

class PlayerListResponse(
    @SerializedName("player") val playerList: List<Player>?
)