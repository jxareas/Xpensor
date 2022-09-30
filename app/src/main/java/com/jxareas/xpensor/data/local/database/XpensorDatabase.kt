package com.jxareas.xpensor.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jxareas.xpensor.data.local.converters.DateConverter
import com.jxareas.xpensor.data.local.converters.TimeConverter
import com.jxareas.xpensor.data.local.dao.AccountDao
import com.jxareas.xpensor.data.local.dao.CategoryDao
import com.jxareas.xpensor.data.local.dao.TransactionDao
import com.jxareas.xpensor.data.local.database.XpensorDatabase.Companion.DATABASE_VERSION
import com.jxareas.xpensor.data.local.model.AccountEntity
import com.jxareas.xpensor.data.local.model.CategoryEntity
import com.jxareas.xpensor.data.local.model.TransactionEntity

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