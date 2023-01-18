package com.jxareas.xpensor.features.accounts.presentation.ui.actions.menu

import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.OnToolbarMenuItemClick
import com.jxareas.xpensor.common.utils.SingleToolbarItemMenuProvider

class AddAccountMenu(onToolbarMenuItemClick: OnToolbarMenuItemClick) :
    SingleToolbarItemMenuProvider(
        menuRes = R.menu.menu_accounts,
        toolbarItemRes = R.id.menu_item_add_new_account,
        onToolbarMenuItemClick = onToolbarMenuItemClick,
    )

