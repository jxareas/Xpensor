package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.jxareas.xpensor.common.extensions.invoke
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryAdapter @Inject constructor(
    private val userPreferences: UserPreferences,
) : ListAdapter<CategoryWithAmountUi, CategoryViewHolder>(
    AsyncDifferConfig.Builder(CategoryDiffCallback).build(),
) {

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    class OnClickListener(val clickListener: (category: CategoryWithAmountUi) -> Unit) {
        fun onClick(category: CategoryWithAmountUi) = clickListener(category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            parent invoke ListItemCategoryBinding::inflate,
            userPreferences,
        ).apply {

            val category by lazy { currentList[bindingAdapterPosition] }

            itemView.setOnClickListener { onClickListener?.onClick(category) }
        }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(currentList[position])
}
