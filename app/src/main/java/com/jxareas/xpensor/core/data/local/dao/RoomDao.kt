package com.jxareas.xpensor.core.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface RoomDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T)

    @Insert
    suspend fun insertAll(vararg items: T)

    @Update
    suspend fun update(item: T)

    @Delete
    suspend fun delete(item: T)

}
