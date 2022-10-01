package com.jxareas.xpensor.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.domain.usecase.ConvertCurrencyUseCase
import com.jxareas.xpensor.ui.converter.event.CurrencyConverterEvent
import com.jxareas.xpensor.ui.converter.state.ConversionState
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
) : ViewModel() {

    private val _conversion = MutableStateFlow<ConversionState>(ConversionState.Idle)
    val conversion = _conversion.asStateFlow()

    private val _events = MutableSharedFlow<CurrencyConverterEvent>()
    val events = _events.asSharedFlow()

    var conversionResult: Double = 0.0

    fun convert(amount: Double, from: String, to: String) {
        viewModelScope.launch {
            _conversion.value = ConversionState.Loading
            conversionResult = convertCurrencyUseCase(amount, from, to)
            _conversion.value =
                when (conversionResult) {
                    ConvertCurrencyUseCase.UNEXPECTED_ERROR ->
                        ConversionState.Error("Unexpected error")
                    ConvertCurrencyUseCase.NO_INTERNET_CONNECTION ->
                        ConversionState.Error("Check your internet connection")
                    else ->
                        ConversionState.Ready(conversionResult.toAmountFormat(withMinus = false))
                }
        }
    }

    fun onSwapButtonClick() {
        viewModelScope.launch {
            _events.emit(CurrencyConverterEvent.Swap)
        }
    }

    fun onConvertButtonClick() {
        viewModelScope.launch {
            _events.emit(CurrencyConverterEvent.Convert)
        }
    }

}