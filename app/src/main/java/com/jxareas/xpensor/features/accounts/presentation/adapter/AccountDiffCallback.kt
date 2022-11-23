package com.jxareas.xpensor.features.accounts.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.features.accounts.domain.model.AccountWithDetails

object AccountDiffCallback : DiffUtil.ItemCallback<AccountWithDetails>() {

    override fun areItemsTheSame(oldItem: AccountWithDetails, newItem: AccountWithDetails): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AccountWithDetails, newItem: AccountWithDetails): Boolean =
        oldItem.hashCode() == newItem.hashCode()

}