package com.jxareas.xpensor.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun ViewModel.launchScoped(
    coroutineScope: CoroutineScope = viewModelScope,
    onLaunch: suspend CoroutineScope.() -> Unit,
) = Unit.also { coroutineScope.launch(block = onLaunch) }