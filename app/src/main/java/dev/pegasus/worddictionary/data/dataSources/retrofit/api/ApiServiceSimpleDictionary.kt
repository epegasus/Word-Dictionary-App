package dev.pegasus.worddictionary.data.dataSources.retrofit.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

interface ApiServiceSimpleDictionary {

    @GET("api/v2/entries/en/{word}")
    suspend fun getMeaning(@Path("word") word: String): Response<ResponseBody>

}