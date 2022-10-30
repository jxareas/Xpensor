package com.jxareas.xpensor.ui.transactions.actions.category.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.data.local.views.CategoryView

object CategoryDiffCallback : DiffUtil.ItemCallback<CategoryView>() {
    override fun areItemsTheSame(oldItem: CategoryView, newItem: CategoryView): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CategoryView, newItem: CategoryView): Boolean =
        oldItem.hashCode() == newItem.hashCode()
}