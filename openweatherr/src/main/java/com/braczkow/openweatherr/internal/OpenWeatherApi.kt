package com.braczkow.openweatherr.internal

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    class Builder(
        private val appId: String
    ) {
        fun build() : OpenWeatherApi {
            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(
                HttpLoggingInterceptor(
                    HttpLoggingInterceptor.Logger { message -> System.out.println(message) }
                ).setLevel(HttpLoggingInterceptor.Level.BODY))

            httpClient.addInterceptor { chain ->
                val updatedUrl = chain
                    .request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(
                        "appid",
                        appId
                    )
                    .build()

                val updatedRequest = chain
                    .request()
                    .newBuilder()
                    .url(updatedUrl)
                    .build()


                chain.proceed(updatedRequest)

            }

            val moshi = Moshi.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .client(httpClient.build())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(OpenWeatherApi::class.java)
        }
    }

    @GET("data/2.5/onecall")
    suspend fun getOneCallByLatLon(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String
    ): OpenWeatherOneCallResponse
}