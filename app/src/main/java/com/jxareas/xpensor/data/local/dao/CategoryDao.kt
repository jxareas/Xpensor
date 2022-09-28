package com.jxareas.xpensor.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.jxareas.xpensor.data.local.views.CategoryView
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface CategoryDao {

    @Query("""
    SELECT id, name, icon, iconColor, ifnull((SELECT SUM(amount) FROM transactions
    WHERE categories.id = categoryId AND date >= :from AND date <= :to), 0) 
    AS category_amount FROM categories GROUP BY id
    """)
    fun getCategoryViews(from: LocalDate, to: LocalDate): Flow<List<CategoryView>>

    @Query("""
    SELECT id, name, icon, iconColor, ifnull((SELECT SUM(amount) FROM transactions
    WHERE categories.id = categoryId AND date >= :from AND date <= :to AND accountId = :id), 0)
    AS category_amount FROM categories GROUP BY id
    """)
    fun getCategoryViewsForAccount(
        from: LocalDate,
        to: LocalDate,
        id: Int,
    ): Flow<List<CategoryView>>
}
