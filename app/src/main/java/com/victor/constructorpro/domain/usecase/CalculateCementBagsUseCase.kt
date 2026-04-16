package com.victor.constructorpro.domain.usecase

import kotlin.math.ceil

data class CementBagsResult(
    val volumeM3: Double,
    val psi: Int,
    val cementKg: Double,
    val bags50Kg: Double,
    val bags42_5Kg: Double,
    val bags25Kg: Double
)

class CalculateCementBagsUseCase {

    fun execute(volumeM3: Double, psi: Int): CementBagsResult {
        require(volumeM3 > 0) {
            "El volumen debe ser mayor que cero"
        }

        val cementKgPerM3 = when (psi) {
            2000 -> 280.0
            2500 -> 320.0
            3000 -> 360.0
            3500 -> 400.0
            else -> throw IllegalArgumentException("PSI no soportado")
        }

        val totalCementKg = volumeM3 * cementKgPerM3

        return CementBagsResult(
            volumeM3 = volumeM3,
            psi = psi,
            cementKg = totalCementKg,
            bags50Kg = totalCementKg / 50.0,
            bags42_5Kg = totalCementKg / 42.5,
            bags25Kg = totalCementKg / 25.0
        )
    }
}