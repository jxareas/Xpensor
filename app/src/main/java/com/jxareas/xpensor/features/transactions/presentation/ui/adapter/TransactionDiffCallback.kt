package com.jxareas.xpensor.features.transactions.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.features.transactions.domain.model.TransactionAmountPerDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails

object TransactionDiffCallback : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
        if (oldItem is TransactionAmountPerDay && newItem is TransactionAmountPerDay)
            oldItem.transactionDate == newItem.transactionDate
        else if (oldItem is TransactionWithDetails && newItem is TransactionWithDetails)
            oldItem.id == newItem.id
        else false

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
        if (oldItem is TransactionAmountPerDay && newItem is TransactionAmountPerDay)
            oldItem.hashCode() == newItem.hashCode()
        else if (oldItem is TransactionWithDetails && newItem is TransactionWithDetails)
            oldItem.hashCode() == newItem.hashCode()
        else false
}
