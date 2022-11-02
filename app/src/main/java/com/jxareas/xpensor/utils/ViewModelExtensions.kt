package com.jxareas.xpensor.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal inline fun <reified VM : ViewModel> VM.launchScoped(
    coroutineScope: CoroutineScope = viewModelScope,
    noinline onLaunch: suspend CoroutineScope.() -> Unit,
) = Unit.also {
    coroutineScope.launch(block = onLaunch)
}

internal suspend inline infix operator fun <reified VB : ViewBinding> ViewGroup.invoke(
    crossinline bindingInflater: suspend LayoutInflater.(parent: ViewGroup, attachToParent: Boolean) -> VB,
): VB = LayoutInflater.from(context).let inflaterScoped@{ layoutInflater ->
    bindingInflater.invoke(layoutInflater, this, false)
}