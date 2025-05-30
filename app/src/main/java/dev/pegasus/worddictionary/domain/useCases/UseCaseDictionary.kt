package dev.pegasus.worddictionary.domain.useCases

import android.util.Log
import dev.pegasus.worddictionary.R
import dev.pegasus.worddictionary.data.repository.RepositoryDictionaryImpl
import dev.pegasus.worddictionary.presentation.uiStates.ApiResponse
import dev.pegasus.worddictionary.utilities.ConstantUtils.TAG
import dev.pegasus.worddictionary.utilities.WebViewUtils
import dev.pegasus.worddictionary.utilities.manager.InternetManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class UseCaseDictionary(
    private val repositoryDictionaryImpl: RepositoryDictionaryImpl,
    private val internetManager: InternetManager,
    private val webViewUtils: WebViewUtils
) {
    suspend fun getDictionary(queryText: String?): Flow<ApiResponse<String>> {
        Log.d(TAG, "UseCaseDictionary: getDictionary: query: $queryText")
        if (queryText?.trim().isNullOrEmpty()) {
            return flowOf(ApiResponse.Failure(R.string.toast_please_enter_text_before_searching))
        }

        if (internetManager.isInternetConnected.not()) {
            return flowOf(ApiResponse.Failure(R.string.toast_no_internet_connection))
        }

        val query = queryText.trim()

        return repositoryDictionaryImpl.getDictionary(query)
            .map { response ->
                when (response) {
                    is ApiResponse.Success -> onApiSuccessResponse(response)
                    is ApiResponse.Loading -> ApiResponse.Loading
                    is ApiResponse.Failure -> response
                }
            }.flowOn(Dispatchers.Default)
            .catch {
                Log.e(TAG, "UseCaseDictionary: getDictionary: Exception:", it)
                emit(ApiResponse.Failure(R.string.toast_something_went_wrong))
            }
    }

    private fun onApiSuccessResponse(response: ApiResponse.Success<String>): ApiResponse.Success<String> {
        val parseJsonDictionary = webViewUtils.getParseJsonDictionary(response.data)
        val webViewText = webViewUtils.getWebViewData(false, parseJsonDictionary)
        val cleanText = webViewUtils.extractCleanText(parseJsonDictionary) // to Share

        return ApiResponse.Success(webViewText)
    }
}