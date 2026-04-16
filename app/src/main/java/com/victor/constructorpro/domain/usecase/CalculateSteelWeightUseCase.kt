package com.victor.constructorpro.domain.usecase

import com.victor.constructorpro.domain.model.SteelCalculationResult

class CalculateSteelWeightUseCase {

    private val pesosPorMetro = mapOf(
        "#2" to 0.25,
        "#3" to 0.56,
        "#4" to 0.99,
        "#5" to 1.55,
        "#6" to 2.24,
        "#7" to 3.04,
        "#8" to 3.97,
        "#9" to 5.06,
        "#10" to 6.40,
        "#12" to 8.94
    )

    fun execute(
        numeroVarilla: String,
        longitud: Double,
        cantidad: Int
    ): SteelCalculationResult {

        val pesoPorMetro = pesosPorMetro[numeroVarilla]
            ?: throw IllegalArgumentException("Varilla no válida")

        val pesoTotal = pesoPorMetro * longitud * cantidad

        return SteelCalculationResult(
            numeroVarilla = numeroVarilla,
            pesoPorMetro = pesoPorMetro,
            longitud = longitud,
            cantidad = cantidad,
            pesoTotal = pesoTotal
        )
    }
}