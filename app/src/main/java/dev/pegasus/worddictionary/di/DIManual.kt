package dev.pegasus.worddictionary.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dev.pegasus.worddictionary.data.dataSources.DataSourceRemoteDictionary
import dev.pegasus.worddictionary.data.dataSources.retrofit.RetrofitInstanceDictionary
import dev.pegasus.worddictionary.data.repository.RepositoryDictionaryImpl
import dev.pegasus.worddictionary.domain.useCases.UseCaseDictionary
import dev.pegasus.worddictionary.presentation.viewModels.ViewModelMain
import dev.pegasus.worddictionary.presentation.viewModels.ViewModelProviderDictionary
import dev.pegasus.worddictionary.utilities.WebViewUtils
import dev.pegasus.worddictionary.utilities.manager.InternetManager

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class DIManual {

    fun getDictionary(context: Context, owner: ViewModelStoreOwner): ViewModelMain {
        val apiInterface = RetrofitInstanceDictionary.api
        val dataSource = DataSourceRemoteDictionary(apiInterface)
        val repository = RepositoryDictionaryImpl(dataSource)
        val internetManager = InternetManager(context)
        val webViewUtils = WebViewUtils()
        val useCase = UseCaseDictionary(repository, internetManager, webViewUtils)
        val factory = ViewModelProviderDictionary(useCase)
        return ViewModelProvider(owner, factory)[ViewModelMain::class.java]
    }
}