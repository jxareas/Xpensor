package com.jxareas.xpensor.common.utils

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.core.view.MenuProvider

abstract class SingleToolbarItemMenuProvider(
    @MenuRes private val menuRes: Int,
    @IdRes private val toolbarItemRes: Int,
    private val onToolbarMenuItemClick: OnToolbarMenuItemClick,
) : MenuProvider {

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(menuRes, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            toolbarItemRes -> true.also { onToolbarMenuItemClick.invoke() }
            else -> false
        }

}
