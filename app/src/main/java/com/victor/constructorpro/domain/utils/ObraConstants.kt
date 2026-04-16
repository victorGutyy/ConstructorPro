package com.victor.constructorpro.domain.utils

object ObraConstants {

    const val PESO_BULTO_CEMENTO_KG = 50.0
    const val PALADAS_POR_BULTO = 12.5
    const val LITROS_POR_BALDE = 20.0

    data class DosificacionObra(
        val arenaPaladas: Double,
        val gravaPaladas: Double,
        val aguaBaldes: Double
    )

    val DOSIFICACION_POR_PSI = mapOf(
        2000 to DosificacionObra(35.0, 45.0, 6.0),
        2500 to DosificacionObra(30.0, 40.0, 5.0),
        3000 to DosificacionObra(25.0, 35.0, 4.5),
        3500 to DosificacionObra(22.0, 30.0, 4.0)
    )
}