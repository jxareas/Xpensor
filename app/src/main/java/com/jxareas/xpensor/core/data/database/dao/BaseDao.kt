package com.jxareas.xpensor.core.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    fun insert(item: T)

    @Insert
    fun insert(vararg item: T)

    @Update
    fun update(item: T)

    @Delete
    fun delete(item: T)
}
