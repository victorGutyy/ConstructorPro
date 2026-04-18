package com.victor.constructorpro.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historial: HistorialEntity)

    @Query("SELECT * FROM historial_calculos ORDER BY id DESC")
    fun getAll(): Flow<List<HistorialEntity>>

    @Query("DELETE FROM historial_calculos")
    suspend fun deleteAll()
}