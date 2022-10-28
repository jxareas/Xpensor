package com.jxareas.xpensor.ui.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jxareas.xpensor.R
import com.jxareas.xpensor.databinding.FragmentConverterBinding
import com.jxareas.xpensor.ui.converter.event.CurrencyConverterEvent
import com.jxareas.xpensor.ui.converter.state.ConversionState
import com.jxareas.xpensor.utils.getCurrentDestination
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
                when(currencyConverterEvent) {
                    is CurrencyConverterEvent.Convert -> handleConvertEvent()
                    is CurrencyConverterEvent.Swap -> handleSwapEvent()
                    is CurrencyConverterEvent.OpenTheAddTransactionSheet -> handleOpenTransactionSheetEvent()
                }

            }
        }
    }

    private fun handleOpenTransactionSheetEvent() {
        if (getCurrentDestination() == this@ConverterFragment.javaClass.name) {
            // TODO: NavDirection Navigation
        }
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