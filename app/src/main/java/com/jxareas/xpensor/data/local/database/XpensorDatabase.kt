package com.jxareas.xpensor.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jxareas.xpensor.data.local.converters.DateConverter
import com.jxareas.xpensor.data.local.converters.TimeConverter
import com.jxareas.xpensor.data.local.dao.AccountsDao
import com.jxareas.xpensor.data.local.dao.CategoriesDao
import com.jxareas.xpensor.data.local.dao.TransactionsDao
import com.jxareas.xpensor.data.local.database.XpensorDatabase.Companion.DATABASE_VERSION
import com.jxareas.xpensor.data.local.entity.Account
import com.jxareas.xpensor.data.local.entity.Category
import com.jxareas.xpensor.data.local.entity.Transaction

@Database(
    entities = [Account::class, Category::class, Transaction::class],
    version = DATABASE_VERSION,
    exportSchema = true
)
@TypeConverters(TimeConverter::class, DateConverter::class)
abstract class XpensorDatabase : RoomDatabase() {

    abstract val accountsDao: AccountsDao
    abstract val categoriesDao: CategoriesDao
    abstract val transactionsDao: TransactionsDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "xpensor.db"
    }
}