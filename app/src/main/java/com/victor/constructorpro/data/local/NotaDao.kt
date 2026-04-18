package com.victor.constructorpro.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nota: NotaEntity)

    @Update
    suspend fun update(nota: NotaEntity)

    @Delete
    suspend fun delete(nota: NotaEntity)

    @Query("SELECT * FROM notas ORDER BY id DESC")
    fun getAll(): Flow<List<NotaEntity>>
}