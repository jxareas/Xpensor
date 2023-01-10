package com.jxareas.xpensor.features.transactions.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.features.transactions.domain.model.TransactionByDay
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails

object TransactionDiffCallback : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
        if (oldItem is TransactionByDay && newItem is TransactionByDay)
            oldItem.date == newItem.date
        else if (oldItem is TransactionDetails && newItem is TransactionDetails)
            oldItem.transaction.id == newItem.transaction.id
        else false

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
        if (oldItem is TransactionByDay && newItem is TransactionByDay)
            oldItem.hashCode() == newItem.hashCode()
        else if (oldItem is TransactionDetails && newItem is TransactionDetails)
            oldItem.hashCode() == newItem.hashCode()
        else false
}
