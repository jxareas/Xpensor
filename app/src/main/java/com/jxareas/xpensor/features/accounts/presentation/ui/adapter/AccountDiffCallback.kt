package com.jxareas.xpensor.features.accounts.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi

object AccountDiffCallback : DiffUtil.ItemCallback<AccountUi>() {

    override fun areItemsTheSame(oldItem: AccountUi, newItem: AccountUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AccountUi, newItem: AccountUi): Boolean =
        oldItem.hashCode() == newItem.hashCode()
}
