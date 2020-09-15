package com.braczkow.wirt.core

import com.braczkow.openweatherr.WillItRainUseCase
import com.braczkow.openweatherr.internal.OpenWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
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
}