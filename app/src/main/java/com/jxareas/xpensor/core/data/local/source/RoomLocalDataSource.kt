package com.jxareas.xpensor.core.data.local.source

import com.jxareas.xpensor.core.data.local.dao.RoomDao

abstract class RoomLocalDataSource<T>(
    private val dao: RoomDao<T>,
) : LocalDataSource<T> {

    override suspend fun insert(item: T) =
        dao.insert(item)

    override suspend fun insertAll(vararg items: T) =
        dao.insertAll(*items)

    override suspend fun update(item: T) =
        dao.update(item)

    override suspend fun delete(item: T) =
        dao.delete(item)
}
