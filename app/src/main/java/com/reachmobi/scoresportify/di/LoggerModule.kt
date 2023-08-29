package com.reachmobi.scoresportify.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.reachmobi.scoresportify.logger.FirebaseLogger
import com.reachmobi.scoresportify.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

    @Provides
    @Singleton
    fun provideFirebaseLogger(firebaseAnalytics: FirebaseAnalytics) : Logger{
        return FirebaseLogger(firebaseAnalytics)
    }
}