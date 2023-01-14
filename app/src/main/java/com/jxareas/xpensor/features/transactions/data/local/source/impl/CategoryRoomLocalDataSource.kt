package com.jxareas.xpensor.features.transactions.data.local.source.impl

import com.jxareas.xpensor.common.extensions.mapEach
import com.jxareas.xpensor.core.data.local.source.RoomLocalDataSource
import com.jxareas.xpensor.features.transactions.data.local.dao.CategoryDao
import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import com.jxareas.xpensor.features.transactions.data.local.source.CategoryLocalDataSource
import com.jxareas.xpensor.features.transactions.data.local.views.CategoryView
import com.jxareas.xpensor.features.transactions.data.mapper.toCategoryWithTotalAmountView
import java.time.LocalDate
import javax.inject.Inject

class CategoryRoomLocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao,
) : RoomLocalDataSource<CategoryEntity>(categoryDao), CategoryLocalDataSource {

    override fun getTransactionAmountByCategoryBetween(from: LocalDate, to: LocalDate) =
        categoryDao.getCategoryViews(from, to)
            .mapEach(CategoryView::toCategoryWithTotalAmountView)

    override fun getTransactionAmountByCategoryForAccountBetween(
        from: LocalDate,
        to: LocalDate,
        accountId: Int,
    ) = categoryDao.getCategoryViewsForAccount(from, to, accountId)
        .mapEach(CategoryView::toCategoryWithTotalAmountView)

}
