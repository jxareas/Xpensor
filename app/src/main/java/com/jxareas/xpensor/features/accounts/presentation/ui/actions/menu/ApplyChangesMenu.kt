package com.jxareas.xpensor.features.accounts.presentation.ui.actions.menu

import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.OnToolbarMenuItemClick
import com.jxareas.xpensor.common.utils.SingleToolbarItemMenuProvider

class ApplyChangesMenu(onToolbarMenuItemClick: OnToolbarMenuItemClick) :
    SingleToolbarItemMenuProvider(
        menuRes = R.menu.menu_apply_changes,
        toolbarItemRes = R.id.menu_item_apply_changes,
        onToolbarMenuItemClick = onToolbarMenuItemClick,
    )
