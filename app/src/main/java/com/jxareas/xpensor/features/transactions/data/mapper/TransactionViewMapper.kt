package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.core.domain.mapper.Mapper
import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionViewMapper @Inject constructor() :
    Mapper<TransactionWithDetails, TransactionView> {

    override fun mapFromDomain(source: TransactionWithDetails): TransactionView =
        TransactionView(
            id = source.id,
            note = source.note,
            amount = source.amount,
            date = source.date,
            time = source.time,
            categoryId = source.category.id,
            categoryName = source.category.name,
            accountId = source.account.id,
            accountName = source.account.name,
            icon = source.category.icon,
            iconColor = source.category.iconColor,
        )

    override fun mapToDomain(destination: TransactionView): TransactionWithDetails =
        TransactionWithDetails(
            id = destination.id,
            note = destination.note,
            amount = destination.amount,
            date = destination.date,
            time = destination.time,
            category = Category(
                destination.categoryId,
                destination.categoryName,
                destination.icon,
                destination.iconColor
            ),
            account = Account(destination.accountId, destination.accountName)
        )
}
