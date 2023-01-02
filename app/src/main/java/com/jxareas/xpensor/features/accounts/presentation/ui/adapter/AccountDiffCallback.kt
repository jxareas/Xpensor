package com.jxareas.xpensor.features.accounts.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.features.accounts.presentation.model.AccountWithDetailsUi

object AccountDiffCallback : DiffUtil.ItemCallback<AccountWithDetailsUi>() {

    override fun areItemsTheSame(oldItem: AccountWithDetailsUi, newItem: AccountWithDetailsUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AccountWithDetailsUi, newItem: AccountWithDetailsUi): Boolean =
        oldItem.hashCode() == newItem.hashCode()
}
