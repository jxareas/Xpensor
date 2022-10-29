package com.jxareas.xpensor.ui.transactions.actions.category.adapter

import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.databinding.ListItemCategoryBinding
import com.jxareas.xpensor.utils.invoke
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryAdapter @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ListAdapter<CategoryView, CategoryViewHolder>(
    AsyncDifferConfig.Builder(CategoryDiffCallback).build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(parent invoke ListItemCategoryBinding::inflate,
            sharedPreferences)

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(currentList[position])


}