package com.victor.constructorpro.domain.usecase

import com.victor.constructorpro.domain.model.FlejesResult
import kotlin.math.ceil

class CalculateFlejesUseCase {

    fun execute(
        longitudTotalElemento: Double,
        espaciamiento: Double
    ): FlejesResult {
        require(longitudTotalElemento > 0) { "La longitud total debe ser mayor a 0" }
        require(espaciamiento > 0) { "El espaciamiento debe ser mayor a 0" }

        val cantidadFlejes = ceil(longitudTotalElemento / espaciamiento).toInt() + 1

        return FlejesResult(
            longitudTotalElemento = longitudTotalElemento,
            espaciamiento = espaciamiento,
            cantidadFlejes = cantidadFlejes
        )
    }
}