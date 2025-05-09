package dev.pegasus.worddictionary.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.pegasus.worddictionary.domain.useCases.UseCaseDictionary

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class ViewModelProviderDictionary(private val useCase: UseCaseDictionary) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ViewModelMain::class.java) -> {
                ViewModelMain(useCase) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}