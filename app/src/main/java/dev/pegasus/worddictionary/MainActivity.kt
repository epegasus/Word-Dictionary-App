package dev.pegasus.worddictionary

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.pegasus.worddictionary.databinding.ActivityMainBinding
import dev.pegasus.worddictionary.di.DIManual
import dev.pegasus.worddictionary.presentation.ui.dialogs.DialogProcessing
import dev.pegasus.worddictionary.presentation.uiStates.ApiResponse

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { DIManual().getDictionary(applicationContext, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreen()
        initObservers()

        binding.svBarInc.setOnQueryTextListener(queryTextListener)
        binding.mbCrossSimpleDictionary.setOnClickListener { onClearClick() }
    }

    private fun fullScreen() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initObservers() {
        var dialogProcessing: DialogProcessing? = null
        viewModel.dictionaryResponseLiveData.observe(this) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    dialogProcessing = DialogProcessing()
                    dialogProcessing?.show(supportFragmentManager, "DialogProcessing")
                }

                is ApiResponse.Failure -> {
                    onErrorResult(dialogProcessing, response.messageResId)
                    dialogProcessing = null
                }

                is ApiResponse.Success -> {
                    onSuccessResult(dialogProcessing, response.data)
                    dialogProcessing = null
                }
            }
        }
    }

    private fun onErrorResult(dialogProcessing: DialogProcessing?, messageResId: Int) {
        dialogProcessing?.dismiss()
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun onSuccessResult(dialogProcessing: DialogProcessing?, htmlData: String) {
        binding.webViewSimpleDictionary.visibility = View.VISIBLE
        binding.mbCrossSimpleDictionary.visibility = View.VISIBLE
        loadWebViewData(htmlData)
        dialogProcessing?.dismiss()
    }

    private fun loadWebViewData(htmlData: String) {
        binding.webViewSimpleDictionary.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
    }

    private fun onClearClick() {
        binding.svBarInc.setQuery("", false)
        loadWebViewData("")
    }

    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            viewModel.getSimpleDictionary(query)
            binding.svBarInc.clearFocus()
            return true
        }
    }
}