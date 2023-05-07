package com.example.coinrate.di

import com.example.coinrate.common.Constants.BASE_URL
import com.example.coinrate.data.remote.CoinGeckoApi
import com.example.coinrate.data.repository.CoinRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUnsplashApi(): CoinGeckoApi {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(CoinGeckoApi::class.java)
    }

    @Provides
    @Singleton
    fun providePhotoRepository(api: CoinGeckoApi): CoinRepository {
        return CoinRepository(api)
    }
}