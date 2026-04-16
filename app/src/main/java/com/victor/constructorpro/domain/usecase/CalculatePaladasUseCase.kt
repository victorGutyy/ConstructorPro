package com.victor.constructorpro.domain.usecase

import com.victor.constructorpro.domain.model.ConversionPaladasResult

class CalculatePaladasUseCase {

    fun execute(
        psi: Int,
        cementBags: Double
    ): ConversionPaladasResult {
        require(cementBags > 0) { "La cantidad de sacos debe ser mayor que cero" }

        val (sandPerBag, gravelPerBag, waterPerBag) = when (psi) {
            2000 -> Triple(6.0, 8.0, 1.5)
            2500 -> Triple(5.0, 7.0, 1.25)
            3000 -> Triple(4.0, 6.0, 1.0)
            3500 -> Triple(3.5, 5.0, 0.9)
            else -> throw IllegalArgumentException("PSI no soportado")
        }

        return ConversionPaladasResult(
            psi = psi,
            cementBags = cementBags,
            sandShovels = cementBags * sandPerBag,
            gravelShovels = cementBags * gravelPerBag,
            waterBuckets = cementBags * waterPerBag
        )
    }
}