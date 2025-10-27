package com.example.advanceretro.network


import com.example.advanceretro.api.JsonPlaceholderApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val json = Json { ignoreUnknownKeys = true }

    private fun httpClient(tokenProvider: () -> String) = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptorProvider.get())
        .addInterceptor(AuthInterceptor(tokenProvider))
        .addInterceptor(ErrorInterceptor())
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    fun create(tokenProvider: () -> String): JsonPlaceholderApi {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(httpClient(tokenProvider))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(JsonPlaceholderApi::class.java)
    }
}