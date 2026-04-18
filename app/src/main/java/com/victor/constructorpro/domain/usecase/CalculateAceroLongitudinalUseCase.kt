package com.victor.constructorpro.domain.usecase

import com.victor.constructorpro.domain.model.AceroLongitudinalResult
import kotlin.math.ceil

class CalculateAceroLongitudinalUseCase {

    fun execute(
        longitudTotalElemento: Double,
        cantidadVarillasElemento: Int,
        longitudComercial: Double,
        traslapo: Double
    ): AceroLongitudinalResult {
        require(longitudTotalElemento > 0) { "La longitud total debe ser mayor a 0" }
        require(cantidadVarillasElemento > 0) { "La cantidad de varillas debe ser mayor a 0" }
        require(longitudComercial > 0) { "La longitud comercial debe ser mayor a 0" }
        require(traslapo >= 0) { "El traslapo no puede ser negativo" }
        require(longitudComercial > traslapo) { "La longitud comercial debe ser mayor que el traslapo" }

        val metrosLinealesTotales = longitudTotalElemento * cantidadVarillasElemento
        val longitudUtilPorVarilla = longitudComercial - traslapo
        val cantidadVarillasComerciales =
            ceil(metrosLinealesTotales / longitudUtilPorVarilla).toInt()

        return AceroLongitudinalResult(
            longitudTotalElemento = longitudTotalElemento,
            cantidadVarillasElemento = cantidadVarillasElemento,
            longitudComercial = longitudComercial,
            traslapo = traslapo,
            metrosLinealesTotales = metrosLinealesTotales,
            longitudUtilPorVarilla = longitudUtilPorVarilla,
            cantidadVarillasComerciales = cantidadVarillasComerciales
        )
    }
}