package com.victor.constructorpro.domain.usecase

import com.victor.constructorpro.domain.model.ConcreteDosificationResult

class CalculateConcreteMaterialsUseCase {

    fun execute(volumeM3: Double, psi: Int): ConcreteDosificationResult {
        require(volumeM3 > 0) { "El volumen debe ser mayor a 0" }

        val base = when (psi) {
            2000 -> MaterialBase(
                cementKg = 280.0,
                sandM3 = 0.56,
                gravelM3 = 0.84,
                waterLiters = 180.0
            )
            2500 -> MaterialBase(
                cementKg = 320.0,
                sandM3 = 0.54,
                gravelM3 = 0.82,
                waterLiters = 185.0
            )
            3000 -> MaterialBase(
                cementKg = 360.0,
                sandM3 = 0.52,
                gravelM3 = 0.80,
                waterLiters = 190.0
            )
            3500 -> MaterialBase(
                cementKg = 400.0,
                sandM3 = 0.50,
                gravelM3 = 0.78,
                waterLiters = 195.0
            )
            else -> throw IllegalArgumentException("PSI no soportado")
        }

        return ConcreteDosificationResult(
            volumeM3 = volumeM3,
            psi = psi,
            cementKg = base.cementKg * volumeM3,
            sandM3 = base.sandM3 * volumeM3,
            gravelM3 = base.gravelM3 * volumeM3,
            waterLiters = base.waterLiters * volumeM3,
            cementBags50Kg = (base.cementKg * volumeM3) / 50,
            cementBags42Kg = (base.cementKg * volumeM3) / 42.5,
            cementBags25Kg = (base.cementKg * volumeM3) / 25
        )
    }
}

private data class MaterialBase(
    val cementKg: Double,
    val sandM3: Double,
    val gravelM3: Double,
    val waterLiters: Double
)