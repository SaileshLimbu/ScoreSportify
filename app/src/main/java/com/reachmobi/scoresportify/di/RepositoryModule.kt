package com.reachmobi.scoresportify.di

import com.reachmobi.scoresportify.data.network.ScoreEndPoints
import com.reachmobi.scoresportify.data.repositories.PlayerRepository
import com.reachmobi.scoresportify.data.repositories.PlayerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun providesPostRepository(endPoint: ScoreEndPoints): PlayerRepository {
        return PlayerRepositoryImpl(endPoint)
    }
}