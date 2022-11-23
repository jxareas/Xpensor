package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails

object TransactionViewMapper : Mapper<TransactionView, TransactionWithDetails> {
    override fun mapTo(source: TransactionView): TransactionWithDetails =
        TransactionWithDetails(id = source.id,
            note = source.note,
            amount = source.amount,
            date = source.date,
            time = source.time,
            category = Category(source.categoryId,
                source.categoryName,
                source.icon,
                source.iconColor),
            account = Account(source.accountId, source.accountName)
        )

    override fun mapFrom(destination: TransactionWithDetails): TransactionView =
        TransactionView(
            id = destination.id,
            note = destination.note,
            amount = destination.amount,
            date = destination.date,
            time = destination.time,
            categoryId = destination.category.id,
            categoryName = destination.category.name,
            accountId = destination.account.id,
            accountName = destination.account.name,
            icon = destination.category.icon,
            iconColor = destination.category.iconColor,
        )
}