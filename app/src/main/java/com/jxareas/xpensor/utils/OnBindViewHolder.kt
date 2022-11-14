package com.jxareas.xpensor.utils

@FunctionalInterface
fun interface OnBindViewHolder<T> {
    fun bind(item : T)
}