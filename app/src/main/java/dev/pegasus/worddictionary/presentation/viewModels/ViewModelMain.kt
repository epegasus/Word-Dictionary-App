package dev.pegasus.worddictionary.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pegasus.worddictionary.domain.useCases.UseCaseDictionary
import dev.pegasus.worddictionary.presentation.uiStates.ApiResponse
import dev.pegasus.worddictionary.utilities.SingleLiveEvent
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

    private val _favouriteLiveData = MutableLiveData<Int>()
    val favouriteLiveData: LiveData<Int> get() = _favouriteLiveData

    private val _toastLiveData = SingleLiveEvent<Int>()
    val toastLiveData: LiveData<Int> get() = _toastLiveData

    private val _shareLiveData = SingleLiveEvent<String>()
    val shareLiveData: LiveData<String> get() = _shareLiveData

    private val _copyLiveData = SingleLiveEvent<String>()
    val copyLiveData: LiveData<String> get() = _copyLiveData

    private val _speakLiveData = SingleLiveEvent<String>()
    val speakLiveData: LiveData<String> get() = _speakLiveData

    private val _translateLiveData = SingleLiveEvent<String>()
    val translateLiveData: LiveData<String> get() = _translateLiveData

    private val _clearLiveData = SingleLiveEvent<Unit>()
    val clearLiveData: LiveData<Unit> get() = _clearLiveData

    fun getSimpleDictionary(query: String?) = viewModelScope.launch {
        _dictionaryResponseLiveData.value = ApiResponse.Loading
        useCaseDictionary
            .getSimpleDictionary(query)
            .collect { response ->
                _dictionaryResponseLiveData.value = response
            }
    }

    fun shareText() {
        _shareLiveData.value = useCaseDictionary.getResultText()
    }

    fun copyText() {
        _copyLiveData.value = useCaseDictionary.getResultText()
    }

    fun speakText() {
        _speakLiveData.value = useCaseDictionary.getResultText()
    }

    fun translateClick() {
        _translateLiveData.value = useCaseDictionary.getResultText()
    }

    fun crossClick() {
        useCaseDictionary.clearResultText()
        _clearLiveData.value = Unit
    }
}