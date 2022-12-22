package com.jxareas.xpensor.common.utils

@FunctionalInterface
fun interface OnBindViewHolder<T> {
    fun bind(item: T)
}
