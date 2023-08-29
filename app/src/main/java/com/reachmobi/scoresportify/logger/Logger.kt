package com.reachmobi.scoresportify.logger

import android.os.Bundle

interface Logger {
    fun logEvent(eventName: String, params: Bundle)
}