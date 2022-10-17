package com.jxareas.xpensor.utils

@FunctionalInterface
fun interface Binder<T> {
    fun bind(item : T)
}