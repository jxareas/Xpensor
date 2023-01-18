package com.jxareas.xpensor.common.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

private typealias SuspendedTransform<T, R> =
        suspend (value: T) -> R

internal inline infix fun <T, R> Flow<List<T>>.mapEach(
    crossinline onElementTransform: SuspendedTransform<T, R>,
): Flow<List<R>> = transform transformationScoped@{ flowCollector ->
    flowCollector
        .map { element ->
            onElementTransform(element)
        }.also { mappingTransform ->
            return@transformationScoped emit(mappingTransform)
        }
}
