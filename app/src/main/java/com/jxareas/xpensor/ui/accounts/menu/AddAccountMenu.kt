package com.jxareas.xpensor.ui.accounts.menu

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.jxareas.xpensor.R
import com.jxareas.xpensor.utils.OnToolbarMenuItemClick


class AddAccountMenu(private val onItemClicked: OnToolbarMenuItemClick) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.accounts_menu, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            R.id.add_new_account -> {
                onItemClicked()
                true
            }
            else -> false
        }
}