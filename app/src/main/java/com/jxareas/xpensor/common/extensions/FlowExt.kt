package com.jxareas.xpensor.common.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun <T, R> Flow<List<T>>.mapList(
    onItemTransform: suspend (value: T) -> R,
): Flow<List<R>> = transform { flowCollector ->
    val transform = flowCollector.map { element -> onItemTransform(element) }
    return@transform emit(transform)
}