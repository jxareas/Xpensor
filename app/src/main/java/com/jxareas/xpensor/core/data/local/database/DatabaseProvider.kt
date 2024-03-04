package com.jxareas.xpensor.core.data.local.database

import android.content.Context
import androidx.room.RoomDatabase

internal sealed interface DatabaseProvider<T> {
    fun provideWithContext(context: Context): T

    interface Room<T : RoomDatabase> : DatabaseProvider<T>
}
