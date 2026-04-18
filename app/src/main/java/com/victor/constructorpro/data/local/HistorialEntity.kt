package com.victor.constructorpro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial_calculos")
data class HistorialEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tipo: String,
    val detalle: String,
    val fechaHora: String
)