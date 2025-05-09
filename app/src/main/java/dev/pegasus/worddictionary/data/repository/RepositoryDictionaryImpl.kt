package dev.pegasus.worddictionary.data.repository

import dev.pegasus.worddictionary.data.dataSources.DataSourceRemoteDictionary
import dev.pegasus.worddictionary.domain.repository.RepositorySimpleDictionary
import dev.pegasus.worddictionary.presentation.uiStates.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */


class RepositoryDictionaryImpl(private val dataSourceRemote: DataSourceRemoteDictionary) : RepositorySimpleDictionary {

    override suspend fun getSimpleDictionary(query: String): Flow<ApiResponse<String>> = withContext(Dispatchers.IO) {
        return@withContext dataSourceRemote.getSimpleDictionary(query)
    }
}