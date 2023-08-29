package com.reachmobi.scoresportify.logger

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseLogger @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : Logger {

    override fun logEvent(eventName: String, params: Bundle) {
        firebaseAnalytics.logEvent(eventName, params)
    }
}