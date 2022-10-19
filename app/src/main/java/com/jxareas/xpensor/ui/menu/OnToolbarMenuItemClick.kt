package com.jxareas.xpensor.ui.menu

@FunctionalInterface
fun interface OnToolbarMenuItemClick {
    operator fun invoke() = onToolbarMenuItemClick()
    fun onToolbarMenuItemClick()
}