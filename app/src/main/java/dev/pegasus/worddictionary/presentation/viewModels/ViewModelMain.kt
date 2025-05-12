package dev.pegasus.worddictionary.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pegasus.worddictionary.domain.useCases.UseCaseDictionary
import dev.pegasus.worddictionary.presentation.uiStates.ApiResponse
import kotlinx.coroutines.launch

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class ViewModelMain(private val useCaseDictionary: UseCaseDictionary) : ViewModel() {

    private val _dictionaryResponseLiveData = MutableLiveData<ApiResponse<String>>()
    val dictionaryResponseLiveData: LiveData<ApiResponse<String>> get() = _dictionaryResponseLiveData

    fun getSimpleDictionary(query: String?) = viewModelScope.launch {
        _dictionaryResponseLiveData.value = ApiResponse.Loading
        useCaseDictionary
            .getDictionary(query)
            .collect { response ->
                _dictionaryResponseLiveData.value = response
            }
    }
}