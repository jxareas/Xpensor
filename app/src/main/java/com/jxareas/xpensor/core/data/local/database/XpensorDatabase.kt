package com.jxareas.xpensor.core.data.local.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jxareas.xpensor.core.data.local.converters.DateConverter
import com.jxareas.xpensor.core.data.local.converters.TimeConverter
import com.jxareas.xpensor.core.data.local.database.XpensorDatabase.Companion.DATABASE_VERSION
import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.accounts.data.local.entity.AccountEntity
import com.jxareas.xpensor.features.transactions.data.local.dao.CategoryDao
import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import com.jxareas.xpensor.features.transactions.data.local.entity.CategoryEntity
import com.jxareas.xpensor.features.transactions.data.local.entity.TransactionEntity
import com.jxareas.xpensor.features.transactions.data.local.views.TransactionView

@Database(
    entities = [AccountEntity::class, CategoryEntity::class, TransactionEntity::class],
    version = DATABASE_VERSION,
    exportSchema = true,
    views = [TransactionView::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ],
)
@TypeConverters(TimeConverter::class, DateConverter::class)
abstract class XpensorDatabase : RoomDatabase() {

    abstract val accountDao: AccountDao
    abstract val categoryDao: CategoryDao
    abstract val transactionDao: TransactionDao

    internal companion object : DatabaseProvider.Room<XpensorDatabase> {
        internal const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "xpensor.db"
        private const val DATABASE_PATH = "database/$DATABASE_NAME"

        private fun xpensorDatabaseBuilder(context: Context) =
            Room.databaseBuilder(context, XpensorDatabase::class.java, DATABASE_NAME)

        override fun provideWithContext(context: Context): XpensorDatabase =
            xpensorDatabaseBuilder(context)
                .createFromAsset(DATABASE_PATH)
                .build()

    }
}
