package com.victor.constructorpro.domain.usecase

data class ResultadoDosificacionObra(
    val psi: Int,
    val bultos: Double,
    val arenaPaladas: Double,
    val gravaPaladas: Double,
    val aguaBaldes: Double,
    val cementoPaladas: Double
)

class CalculateDosificacionObraUseCase {

    fun execute(psi: Int, bultos: Double): ResultadoDosificacionObra {
        require(bultos > 0) {
            "La cantidad de bultos debe ser mayor que cero"
        }

        val cementoPaladasPorBulto = 12.5

        val (arenaPorBulto, gravaPorBulto, aguaPorBulto) = when (psi) {
            2000 -> Triple(35.0, 45.0, 6.0)
            2500 -> Triple(30.0, 40.0, 5.0)
            3000 -> Triple(25.0, 35.0, 4.5)
            3500 -> Triple(22.0, 30.0, 4.0)
            else -> throw IllegalArgumentException("PSI no soportado")
        }

        return ResultadoDosificacionObra(
            psi = psi,
            bultos = bultos,
            arenaPaladas = arenaPorBulto * bultos,
            gravaPaladas = gravaPorBulto * bultos,
            aguaBaldes = aguaPorBulto * bultos,
            cementoPaladas = cementoPaladasPorBulto * bultos
        )
    }
}