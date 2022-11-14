package com.jxareas.xpensor.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal inline fun <reified VM : ViewModel> VM.launchScoped(
    coroutineScope: CoroutineScope = viewModelScope,
    noinline onLaunch: suspend CoroutineScope.() -> Unit,
) = Unit.also {
    coroutineScope.launch(block = onLaunch)
}
