package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.features.transactions.presentation.model.UiCategoryWithAmount

object CategoryDiffCallback : DiffUtil.ItemCallback<UiCategoryWithAmount>() {

    override fun areItemsTheSame(
        oldItem: UiCategoryWithAmount,
        newItem: UiCategoryWithAmount,
    ): Boolean = oldItem.category.id == newItem.category.id

    override fun areContentsTheSame(
        oldItem: UiCategoryWithAmount,
        newItem: UiCategoryWithAmount,
    ): Boolean = oldItem.hashCode() == newItem.hashCode()

}