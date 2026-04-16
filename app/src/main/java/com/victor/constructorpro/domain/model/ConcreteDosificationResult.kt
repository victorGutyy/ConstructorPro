package com.victor.constructorpro.domain.model

data class ConcreteDosificationResult(
    val volumeM3: Double,
    val psi: Int,
    val cementKg: Double,
    val sandM3: Double,
    val gravelM3: Double,
    val waterLiters: Double,
    val cementBags50Kg: Double,
    val cementBags42Kg: Double,
    val cementBags25Kg: Double
)