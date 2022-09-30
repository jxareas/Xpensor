package com.jxareas.xpensor.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.xpensor.domain.usecase.ConvertCurrencyUseCase
import com.jxareas.xpensor.ui.converter.state.ConversionState
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
) : ViewModel() {

    private val _conversion = MutableStateFlow<ConversionState>(ConversionState.Idle)
    val conversion = _conversion.asStateFlow()

    var resultValue: Double = 0.0

    fun convert(amount: Double, from: String, to: String) {
        viewModelScope.launch {
            _conversion.value = ConversionState.Loading
            resultValue = convertCurrencyUseCase(amount, from, to)
            _conversion.value =
                when (resultValue) {
                    -1.0 -> ConversionState.Error("Unexpected error")
                    -2.0 -> ConversionState.Error("Check your internet connection")
                    else -> ConversionState.Ready(resultValue.toAmountFormat(withMinus = false))
                }
        }
    }

}