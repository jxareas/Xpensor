package com.jxareas.xpensor.common.utils

@FunctionalInterface
fun interface OnToolbarMenuItemClick {
    operator fun invoke() = onToolbarMenuItemClick()
    fun onToolbarMenuItemClick()
}