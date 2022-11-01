package com.jxareas.xpensor.ui.transactions.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jxareas.xpensor.data.local.views.DayInformationView
import com.jxareas.xpensor.data.local.views.TransactionView

object TransactionDiffCallback : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
        if (oldItem is DayInformationView && newItem is DayInformationView)
            oldItem.transactionDate == newItem.transactionDate
        else if (oldItem is TransactionView && newItem is TransactionView)
            oldItem.id == newItem.id
        else false

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
        if (oldItem is DayInformationView && newItem is DayInformationView)
            oldItem.hashCode() == newItem.hashCode()
        else if (oldItem is TransactionView && newItem is TransactionView)
            oldItem.hashCode() == newItem.hashCode()
        else false

}