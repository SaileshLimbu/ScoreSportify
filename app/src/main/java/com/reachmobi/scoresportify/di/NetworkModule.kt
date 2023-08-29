package com.reachmobi.scoresportify.di

import android.content.Context
import com.reachmobi.scoresportify.data.network.ScoreEndPoints
import com.reachmobi.scoresportify.utils.Constants
import com.reachmobi.scoresportify.utils.NetworkConnectivityHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.URL.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providesEndPoints(retrofit: Retrofit): ScoreEndPoints {
        return retrofit.create(ScoreEndPoints::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityHelper(@ApplicationContext context: Context): NetworkConnectivityHelper {
        return NetworkConnectivityHelper(context)
    }
}