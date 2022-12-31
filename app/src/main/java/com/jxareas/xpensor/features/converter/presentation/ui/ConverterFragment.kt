package com.jxareas.xpensor.features.converter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.extensions.getLong
import com.jxareas.xpensor.common.extensions.toast
import com.jxareas.xpensor.databinding.FragmentConverterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
        setupConversionCollector()
        setupEventCollector()
        setupListeners()
    }

    private fun setupListeners() = binding.run {
        convertButton.setOnClickListener { viewModel.onConvertButtonClick() }
        swapButton.setOnClickListener { viewModel.onSwapButtonClick() }
    }

    private fun setupConversionCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collectLatest { conversionState ->
                when (conversionState) {
                    is ConversionState.Ready -> {
                        with(binding) {
                            progressBar.isVisible = false
                            resultText.text = conversionState.result
                            addTransactionButton.isClickable = true
                            addTransactionButton.alpha = 1f
                        }
                    }
                    is ConversionState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is ConversionState.Error -> {
                        binding.progressBar.isVisible = false
                        toast(requireContext(), conversionState.error)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupEventCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.events.collectLatest { currencyConverterEvent ->
                when (currencyConverterEvent) {
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
            viewModel.convert(amount, from, to)
        } else toast(requireContext(), getString(R.string.enter_amount_error))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
