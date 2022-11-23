package com.jxareas.xpensor.features.transactions.presentation.adapter


import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jxareas.xpensor.databinding.CardItemTransactionBinding
import com.jxareas.xpensor.databinding.ListItemDayInformationBinding
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import com.jxareas.xpensor.common.utils.OnBindViewHolder
import com.jxareas.xpensor.common.extensions.invoke
import javax.inject.Inject

class TransactionAdapter @Inject constructor(
    private val preferences: SharedPreferences,
) : ListAdapter<Any, TransactionAdapter.ViewHolder>(
    AsyncDifferConfig.Builder(TransactionDiffCallback).build()
) {
    abstract class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root),
        OnBindViewHolder<Any>

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
                TransactionsByDayViewHolder(parent invoke ListItemDayInformationBinding::inflate,
                    preferences)
        }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        when (holder.itemViewType) {
            TRANSACTION_VIEW_TYPE -> {
                val viewHolder = holder as TransactionViewHolder
                viewHolder.bind(currentList[position])
            }
            else -> {
                val viewHolder = holder as TransactionsByDayViewHolder
                viewHolder.bind(currentList[position])
            }
        }

    override fun getItemViewType(position: Int): Int =
        if (currentList[position] is TransactionWithDetails)
            TRANSACTION_VIEW_TYPE
        else DAY_INFO_VIEW_TYPE


}