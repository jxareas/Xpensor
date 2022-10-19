package com.jxareas.xpensor.utils

@FunctionalInterface
fun interface OnToolbarMenuItemClick {
    operator fun invoke() = onToolbarMenuItemClick()
    fun onToolbarMenuItemClick()
}