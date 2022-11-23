package com.jxareas.xpensor.features.transactions.data.mapper

import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView
import com.jxareas.xpensor.core.domain.mapper.DomainMapper
import com.jxareas.xpensor.features.accounts.domain.model.Account
import com.jxareas.xpensor.features.transactions.domain.model.Category
import com.jxareas.xpensor.features.transactions.domain.model.TransactionWithDetails

object TransactionViewMapper : DomainMapper<TransactionView, TransactionWithDetails> {
    override fun toDomain(entity: TransactionView): TransactionWithDetails =
        TransactionWithDetails(id = entity.id,
            note = entity.note,
            amount = entity.amount,
            date = entity.date,
            time = entity.time,
            category = Category(entity.categoryId,
                entity.categoryName,
                entity.icon,
                entity.iconColor),
            account = Account(entity.accountId, entity.accountName)
        )

    override fun fromDomain(domain: TransactionWithDetails): TransactionView =
        TransactionView(
            id = domain.id,
            note = domain.note,
            amount = domain.amount,
            date = domain.date,
            time = domain.time,
            categoryId = domain.category.id,
            categoryName = domain.category.name,
            accountId = domain.account.id,
            accountName = domain.account.name,
            icon = domain.category.icon,
            iconColor = domain.category.iconColor,
        )
}