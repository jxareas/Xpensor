package com.jxareas.xpensor.features.transactions.presentation.actions.category.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.features.transactions.domain.model.CategoryWithDetails

object CategoryDiffCallback : DiffUtil.ItemCallback<CategoryWithDetails>() {

    override fun areItemsTheSame(
        oldItem: CategoryWithDetails,
        newItem: CategoryWithDetails,
    ): Boolean =
        oldItem.category.id == newItem.category.id

    override fun areContentsTheSame(
        oldItem: CategoryWithDetails,
        newItem: CategoryWithDetails,
    ): Boolean =
        oldItem.hashCode() == newItem.hashCode()

}