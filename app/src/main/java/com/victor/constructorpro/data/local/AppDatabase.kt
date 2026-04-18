package com.victor.constructorpro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistorialEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historialDao(): HistorialDao
}