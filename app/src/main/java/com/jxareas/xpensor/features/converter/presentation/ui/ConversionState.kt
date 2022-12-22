package com.jxareas.xpensor.features.converter.presentation.ui

sealed class ConversionState {
    class Ready(val result: String) : ConversionState()
    class Error(val error: String) : ConversionState()
    object Loading : ConversionState()
    object Idle : ConversionState()
}
