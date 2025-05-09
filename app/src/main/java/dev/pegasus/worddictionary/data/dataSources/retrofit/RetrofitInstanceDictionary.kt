package dev.pegasus.worddictionary.data.dataSources.retrofit

import dev.pegasus.worddictionary.data.dataSources.retrofit.api.ApiServiceSimpleDictionary
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

object RetrofitInstanceDictionary {

    private const val BASE_URL = "https://api.dictionaryapi.dev/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Log EVERYTHING: URL, headers, body, etc.
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .build()

    val api: ApiServiceSimpleDictionary by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create()) // plain string response
            .client(client)
            .build()
            .create(ApiServiceSimpleDictionary::class.java)
    }
}