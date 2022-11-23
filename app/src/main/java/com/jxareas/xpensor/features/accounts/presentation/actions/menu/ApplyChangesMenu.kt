package com.jxareas.xpensor.features.accounts.presentation.actions.menu

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.OnToolbarMenuItemClick

class ApplyChangesMenu(private val onItemClicked: OnToolbarMenuItemClick) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.apply_changes_menu, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
        when(menuItem.itemId) {
            R.id.apply_changes -> true.also { onItemClicked() }
            else -> false
        }
}