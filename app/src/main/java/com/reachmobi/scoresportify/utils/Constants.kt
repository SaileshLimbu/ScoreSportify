package com.reachmobi.scoresportify.utils

object Constants {
    object URL{
        const val BASE_URL = "https://www.thesportsdb.com"
        private const val VERSION = "v1"
        private const val API_KEY = "50130162"
        const val SEARCH_PLAYER = "$BASE_URL/api/$VERSION/json/$API_KEY/searchplayers.php"
    }
}
