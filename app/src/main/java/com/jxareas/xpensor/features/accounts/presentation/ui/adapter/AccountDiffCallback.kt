package com.jxareas.xpensor.features.accounts.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.features.accounts.presentation.model.AccountListItem

object AccountDiffCallback : DiffUtil.ItemCallback<AccountListItem>() {

    override fun areItemsTheSame(oldItem: AccountListItem, newItem: AccountListItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AccountListItem, newItem: AccountListItem): Boolean =
        oldItem.hashCode() == newItem.hashCode()

}