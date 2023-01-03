package com.jxareas.xpensor.core.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jxareas.xpensor.features.accounts.data.local.dao.AccountDao
import com.jxareas.xpensor.features.transactions.data.local.dao.CategoryDao
import com.jxareas.xpensor.features.transactions.data.local.dao.TransactionDao
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
abstract class XpensorDatabaseTest {

    private lateinit var database: XpensorDatabase

    protected val accountDao: AccountDao by lazy {
        database.accountDao
    }

    protected val transactionDao: TransactionDao by lazy {
        database.transactionDao
    }

    protected val categoryDao: CategoryDao by lazy {
        database.categoryDao
    }

    @Before
    fun initDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            XpensorDatabase::class.java,
        ).allowMainThreadQueries().build()

    }

    @After
    @kotlin.jvm.Throws(IOException::class)
    fun closeDatabase() = database.close()

}
