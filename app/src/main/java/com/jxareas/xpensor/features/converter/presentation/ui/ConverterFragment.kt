package com.jxareas.xpensor.features.converter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.extensions.getLong
import com.jxareas.xpensor.common.extensions.showToast
import com.jxareas.xpensor.databinding.FragmentConverterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConverterFragment : Fragment() {

    private var _binding: FragmentConverterBinding? = null
    private val binding: FragmentConverterBinding
        get() = _binding!!

    private val viewModel: ConverterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            interpolator = AnticipateOvershootInterpolator()
            duration = resources.getLong(R.integer.material_motion_duration_long_1)
            setPathMotion(MaterialArcMotion())
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            interpolator = AnticipateOvershootInterpolator()
            duration = resources.getLong(R.integer.material_motion_duration_long_1)
            setPathMotion(MaterialArcMotion())
        }
        exitTransition = MaterialFade()
    }

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
        setupEventCollector()
        setupListeners()
    }

    private fun setupListeners() = binding.run {
        convertButton.setOnClickListener { viewModel.onConvertButtonClick() }
        swapButton.setOnClickListener { viewModel.onSwapCurrenciesClick() }
    }

    private fun setupCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.conversionState
                .flowWithLifecycle(lifecycle)
                .collectLatest { conversionState ->
                    when (conversionState) {
                        is ConversionState.Ready -> {
                            with(binding) {
                                progressBarConverter.isVisible = false
                                resultText.text = conversionState.result
                                addTransactionButton.isClickable = true
                                addTransactionButton.alpha = 1f
                            }
                        }
                        is ConversionState.Loading -> {
                            binding.progressBarConverter.isVisible = true
                        }
                        is ConversionState.Error -> {
                            binding.progressBarConverter.isVisible = false
                            showToast(conversionState.error)
                        }
                        else -> Unit
                    }
                }
        }
    }

    private fun setupEventCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventSource
                .flowWithLifecycle(lifecycle)
                .collectLatest { convertCurrencyUiEvent ->
                    when (convertCurrencyUiEvent) {
                        is ConvertCurrencyUiEvent.Convert ->
                            handleConvertEvent()
                        is ConvertCurrencyUiEvent.Swap ->
                            handleSwapEvent()
                        is ConvertCurrencyUiEvent.OpenTheAddTransactionSheet ->
                            handleOpenTransactionSheetEvent()
                    }
                }
        }
    }

    private fun handleOpenTransactionSheetEvent() {
        // TODO : Handle Open the Transaction Sheet
    }

    private fun handleSwapEvent() {
        val fromPosition = binding.spinnerFrom.selectedItemPosition
        val toPosition = binding.spinnerTo.selectedItemPosition

        binding.spinnerFrom.setSelection(toPosition)
        binding.spinnerTo.setSelection(fromPosition)
    }

    private fun handleConvertEvent() {
        val amount =
            binding.amountTextField.editText?.text.toString().toDoubleOrNull()
        val from = binding.spinnerFrom.selectedItem.toString()
        val to = binding.spinnerTo.selectedItem.toString()

        if (amount != null) {
            viewModel.convertCurrency(amount, from, to)
        } else showToast(R.string.enter_amount_error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
