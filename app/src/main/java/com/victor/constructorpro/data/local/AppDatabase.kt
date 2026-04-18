package com.victor.constructorpro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistorialEntity::class, NotaEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historialDao(): HistorialDao
    abstract fun notaDao(): NotaDao
}