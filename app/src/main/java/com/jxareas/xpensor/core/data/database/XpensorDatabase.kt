package com.jxareas.xpensor.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jxareas.xpensor.core.data.local.converters.DateConverter
import com.jxareas.xpensor.core.data.local.converters.TimeConverter
import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.transactions.data.local.dao.CategoryDao
import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import com.jxareas.xpensor.core.data.database.XpensorDatabase.Companion.DATABASE_VERSION
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity

@Database(
    entities = [AccountEntity::class, CategoryEntity::class, TransactionEntity::class],
    version = DATABASE_VERSION,
    exportSchema = true
)
@TypeConverters(TimeConverter::class, DateConverter::class)
abstract class XpensorDatabase : RoomDatabase() {

    abstract val accountDao: AccountDao
    abstract val categoryDao: CategoryDao
    abstract val transactionDao: TransactionDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "xpensor.db"
    }
}