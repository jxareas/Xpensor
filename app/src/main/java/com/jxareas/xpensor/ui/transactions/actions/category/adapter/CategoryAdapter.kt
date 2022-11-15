package com.jxareas.xpensor.ui.transactions.actions.category.adapter

import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.utils.extensions.invoke
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryAdapter @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ListAdapter<CategoryView, CategoryViewHolder>(
    AsyncDifferConfig.Builder(CategoryDiffCallback).build()
) {

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    class OnClickListener(val clickListener: (category: CategoryView) -> Unit) {
        fun onClick(category: CategoryView) = clickListener(category)
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