package com.jxareas.xpensor.features.accounts.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.features.accounts.presentation.model.UiAccount

object AccountDiffCallback : DiffUtil.ItemCallback<UiAccount>() {

    override fun areItemsTheSame(oldItem: UiAccount, newItem: UiAccount): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UiAccount, newItem: UiAccount): Boolean =
        oldItem.hashCode() == newItem.hashCode()

}