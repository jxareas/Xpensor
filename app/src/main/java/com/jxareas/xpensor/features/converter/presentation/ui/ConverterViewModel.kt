package com.jxareas.xpensor.features.converter.presentation.ui

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.features.converter.domain.usecase.ConvertCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
) : ViewModel() {

    private val _conversion = MutableStateFlow<ConversionState>(ConversionState.Idle)
    val conversion = _conversion.asStateFlow()

    private val _eventEmitter = Channel<CurrencyConversionEvent>(Channel.UNLIMITED)
    val eventSource = _eventEmitter.receiveAsFlow()

    var conversionResult: Double = 0.0

    fun convertCurrency(amount: Double, sourceCurrency: String, destinationCurrency: String) =
        launchScoped {
            _conversion.value = ConversionState.Loading
            conversionResult =
                convertCurrencyUseCase.invoke(amount, sourceCurrency, destinationCurrency)
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

    fun onSwapCurrenciesClick() = launchScoped {
        _eventEmitter.send(CurrencyConversionEvent.Swap)
    }

    fun onConvertButtonClick() = launchScoped {
        _eventEmitter.send(CurrencyConversionEvent.Convert)
    }
}
