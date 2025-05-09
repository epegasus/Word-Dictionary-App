package dev.pegasus.worddictionary.domain.repository

import dev.pegasus.worddictionary.presentation.uiStates.ApiResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

interface RepositorySimpleDictionary {
    suspend fun getSimpleDictionary(query: String): Flow<ApiResponse<String>>

}