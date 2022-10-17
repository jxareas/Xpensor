package com.jxareas.xpensor.ui.transactions.adapter


import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jxareas.xpensor.data.local.views.TransactionView
import com.jxareas.xpensor.databinding.CardItemTransactionBinding
import com.jxareas.xpensor.databinding.ListItemDayInformationBinding
import com.jxareas.xpensor.utils.Binder
import com.jxareas.xpensor.utils.invoke
import javax.inject.Inject

class TransactionCardAdapter @Inject constructor(
    private val preferences: SharedPreferences,
) : ListAdapter<Any, TransactionCardAdapter.ViewHolder>(
    AsyncDifferConfig.Builder(TransactionDiffCallback).build()
) {
    abstract class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root),
        Binder<Any>

    companion object {
        private const val DAY_INFO_VIEW_TYPE = 0
        private const val TRANSACTION_VIEW_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            TRANSACTION_VIEW_TYPE ->
                TransactionViewHolder(parent invoke CardItemTransactionBinding::inflate,
                    preferences)
            else ->
                DayInformationViewHolder(parent invoke ListItemDayInformationBinding::inflate,
                    preferences)
        }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        when (holder.itemViewType) {
            TRANSACTION_VIEW_TYPE -> {
                val viewHolder = holder as TransactionViewHolder
                viewHolder.bind(currentList[position])
            }
            else -> {
                val viewHolder = holder as DayInformationViewHolder
                viewHolder.bind(currentList[position])
            }
        }

    override fun getItemViewType(position: Int): Int =
        if (currentList[position] is TransactionView) TRANSACTION_VIEW_TYPE else DAY_INFO_VIEW_TYPE


}