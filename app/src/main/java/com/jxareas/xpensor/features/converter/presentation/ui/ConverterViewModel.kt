package com.jxareas.xpensor.features.converter.presentation.ui

import androidx.lifecycle.ViewModel
import com.jxareas.xpensor.common.extensions.launchScoped
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.features.converter.domain.usecase.ConvertCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
) : ViewModel() {

    private val _conversion = MutableStateFlow<ConversionState>(ConversionState.Idle)
    val conversion = _conversion.asStateFlow()

    private val _events = MutableSharedFlow<ConvertCurrencyUiEvent>()
    val events = _events.asSharedFlow()

    var conversionResult: Double = 0.0

    fun convert(amount: Double, from: String, to: String) = launchScoped {
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

    fun onSwapButtonClick() = launchScoped {
        _events.emit(ConvertCurrencyUiEvent.Swap)
    }

    fun onConvertButtonClick() = launchScoped {
        _events.emit(ConvertCurrencyUiEvent.Convert)
    }
}
