package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category.adapter

import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.jxareas.xpensor.common.extensions.invoke
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.features.transactions.presentation.model.UiCategoryWithAmount
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryAdapter @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ListAdapter<UiCategoryWithAmount, CategoryViewHolder>(
    AsyncDifferConfig.Builder(CategoryDiffCallback).build()
) {

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    class OnClickListener(val clickListener: (category: UiCategoryWithAmount) -> Unit) {
        fun onClick(category: UiCategoryWithAmount) = clickListener(category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(parent invoke ListItemCategoryBinding::inflate,
            sharedPreferences).apply {

            val category by lazy { currentList[bindingAdapterPosition] }

            itemView.setOnClickListener { onClickListener?.onClick(category) }
        }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(currentList[position])


}