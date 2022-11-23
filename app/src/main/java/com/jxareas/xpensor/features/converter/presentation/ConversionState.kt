package com.jxareas.xpensor.features.converter.presentation

sealed class ConversionState {
        class Ready(val result: String) : ConversionState()
        class Error(val error: String) : ConversionState()
        object Loading : ConversionState()
        object Idle : ConversionState()
}