package com.jxareas.xpensor.ui.accounts.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.domain.model.Account

object AccountDiffCallback : DiffUtil.ItemCallback<Account>() {

    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean =
        oldItem.hashCode() == newItem.hashCode()

}