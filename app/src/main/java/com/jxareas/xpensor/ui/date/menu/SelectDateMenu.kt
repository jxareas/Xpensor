package com.jxareas.xpensor.ui.date.menu

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.jxareas.xpensor.R
import com.jxareas.xpensor.utils.OnToolbarMenuItemClick

class SelectDateMenu(private val onToolbarMenuItemClick: OnToolbarMenuItemClick) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.select_date_menu, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            R.id.card_view_select_date -> {
                onToolbarMenuItemClick()
                true
            }
            else -> false
        }
}