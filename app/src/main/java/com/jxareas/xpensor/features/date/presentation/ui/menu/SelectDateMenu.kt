package com.jxareas.xpensor.features.date.presentation.ui.menu

import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.OnToolbarMenuItemClick
import com.jxareas.xpensor.common.utils.SingleToolbarItemMenuProvider

class SelectDateMenu(onToolbarMenuItemClick: OnToolbarMenuItemClick) :
    SingleToolbarItemMenuProvider(
        menuRes = R.menu.menu_select_date,
        toolbarItemRes = R.id.menu_item_select_date,
        onToolbarMenuItemClick = onToolbarMenuItemClick
    )
