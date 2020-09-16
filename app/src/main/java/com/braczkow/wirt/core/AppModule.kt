package com.braczkow.wirt.core

import android.content.Context
import com.braczkow.location.LocationApi
import com.braczkow.location.LocationApiImpl
import com.braczkow.openweatherr.WillItRainUseCase
import com.braczkow.openweatherr.internal.OpenWeatherApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideWillItRainUseCase(): WillItRainUseCase {
        return WillItRainUseCase(
            OpenWeatherApi
                .Builder("ccbab75d0272f13370167dd604890713")
                .build()
        )
    }

    @Provides
    @Singleton
    fun bindLocationApi(
        @ApplicationContext context: Context
    ): LocationApi {
        return LocationApiImpl(context)
    }
}