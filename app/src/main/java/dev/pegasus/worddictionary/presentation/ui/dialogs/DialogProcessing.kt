package dev.pegasus.worddictionary.presentation.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.pegasus.worddictionary.databinding.DialogProcessingBinding

/**
 * Created by: Sohaib Ahmed
 * Date: 5/9/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */


class DialogProcessing : DialogFragment() {

    private var _binding: DialogProcessingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogProcessingBinding.inflate(layoutInflater)
        (binding.root.parent as? ViewGroup)?.removeView(binding.root)

        isCancelable = false

        return MaterialAlertDialogBuilder(binding.root.context)
            .setView(binding.root)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}