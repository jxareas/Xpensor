package com.jxareas.xpensor.core.data.local.source

interface LocalDataSource<T> {

    suspend fun insert(item: T)

    suspend fun insertAll(vararg items: T)

    suspend fun update(item: T)

    suspend fun delete(item: T)

}
