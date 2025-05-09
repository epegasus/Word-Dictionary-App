package dev.pegasus.worddictionary.data.dataSources

import android.util.Log
import dev.pegasus.worddictionary.R
import dev.pegasus.worddictionary.data.dataSources.retrofit.api.ApiServiceSimpleDictionary
import dev.pegasus.worddictionary.presentation.uiStates.ApiResponse
import dev.pegasus.worddictionary.utilities.ConstantUtils.TAG
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class DataSourceRemoteDictionary(private val apiServiceSimpleDictionary: ApiServiceSimpleDictionary) {

    fun getSimpleDictionary(query: String): Flow<ApiResponse<String>> = callbackFlow {
        try {
            val response: Response<ResponseBody> = apiServiceSimpleDictionary.getMeaning(query)
            val bodyString = response.body()?.string()
            Log.d(TAG, "DataSourceSimpleDictionary: getSimpleDictionary: Response: isSuccessful: ${response.isSuccessful}, body: $bodyString")
            if (response.isSuccessful) {
                bodyString?.let {
                    trySend(ApiResponse.Success(it))
                } ?: trySend(ApiResponse.Failure(R.string.toast_no_result_found)) // If body is empty, send error
            } else {
                trySend(ApiResponse.Failure(R.string.toast_no_result_found))
            }
        } catch (ex: Exception) {
            Log.e(TAG, "DataSourceSimpleDictionary: getSimpleDictionary: Exception: ", ex)
            trySend(ApiResponse.Failure(R.string.toast_something_went_wrong)) // Emit error
        }
        awaitClose {}
    }
}