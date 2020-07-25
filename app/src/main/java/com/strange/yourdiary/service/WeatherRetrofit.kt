package com.strange.yourdiary.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRetrofit {

    private var userRetrofit: Retrofit? = null

    private fun getRetrofit(): Retrofit? {
        if (userRetrofit == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            userRetrofit =
                Retrofit.Builder()
                    .baseUrl("")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return userRetrofit
    }
}