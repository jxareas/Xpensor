package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi

object CategoryDiffCallback : DiffUtil.ItemCallback<CategoryWithAmountUi>() {

    override fun areItemsTheSame(
        oldItem: CategoryWithAmountUi,
        newItem: CategoryWithAmountUi,
    ): Boolean = oldItem.category.id == newItem.category.id

    override fun areContentsTheSame(
        oldItem: CategoryWithAmountUi,
        newItem: CategoryWithAmountUi,
    ): Boolean = oldItem.hashCode() == newItem.hashCode()
}
