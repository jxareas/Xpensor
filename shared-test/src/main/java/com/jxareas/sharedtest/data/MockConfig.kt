package com.jxareas.sharedtest.data

private const val NUMBER_OF_MOCKS = 20
private const val INDEX_OFFSET = 287


internal inline fun <reified T> mockList(
    crossinline initializer: (index: Int) -> T,
): List<T> = List(NUMBER_OF_MOCKS) { index ->
    initializer(index + INDEX_OFFSET)
}
