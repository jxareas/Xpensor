package com.jxareas.xpensor.ui.transactions.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.domain.model.TransactionWithDetails
import com.jxareas.xpensor.domain.model.TransactionsByDate

object TransactionDiffCallback : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
        if (oldItem is TransactionsByDate && newItem is TransactionsByDate)
            oldItem.transactionDate == newItem.transactionDate
        else if (oldItem is TransactionWithDetails && newItem is TransactionWithDetails)
            oldItem.id == newItem.id
        else false

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
        if (oldItem is TransactionsByDate && newItem is TransactionsByDate)
            oldItem.hashCode() == newItem.hashCode()
        else if (oldItem is TransactionWithDetails && newItem is TransactionWithDetails)
            oldItem.hashCode() == newItem.hashCode()
        else false

}