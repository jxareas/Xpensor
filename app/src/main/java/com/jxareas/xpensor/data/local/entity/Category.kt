package com.jxareas.xpensor.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val icon: Int,
    val iconColor: String
)