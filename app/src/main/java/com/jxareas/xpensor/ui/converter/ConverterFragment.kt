package com.jxareas.xpensor.ui.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jxareas.xpensor.databinding.FragmentConverterBinding
import com.jxareas.xpensor.ui.converter.state.ConversionState
import com.jxareas.xpensor.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ConverterFragment : Fragment() {

    private var _binding: FragmentConverterBinding? = null
    private val binding: FragmentConverterBinding
        get() = _binding!!

    private val viewModel: ConverterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCollectors()
    }

    private fun setupCollectors() {
        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collectLatest {
                when (it) {
                    is ConversionState.Ready -> {
                        with(binding) {
                            progressBar.isVisible = false
                            resultText.text = it.result
                            addTransactionButton.isClickable = true
                            addTransactionButton.alpha = 1f
                        }
                    }
                    is ConversionState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is ConversionState.Error -> {
                        binding.progressBar.isVisible = false
                        toast(requireContext(), it.error)
                    }
                    else -> Unit
                }
            }
        }
    }

}