package com.victor.constructorpro.domain.usecase

import com.victor.constructorpro.domain.model.DosificacionData

class GetDosificacionByPsiUseCase {

    fun execute(psi: Int): DosificacionData {
        return when (psi) {
            2000 -> DosificacionData(
                psi = 2000,
                cementoKg = 280.0,
                arenaM3 = 0.56,
                gravaM3 = 0.84,
                aguaLitros = 180.0
            )

            2500 -> DosificacionData(
                psi = 2500,
                cementoKg = 320.0,
                arenaM3 = 0.54,
                gravaM3 = 0.82,
                aguaLitros = 185.0
            )

            3000 -> DosificacionData(
                psi = 3000,
                cementoKg = 360.0,
                arenaM3 = 0.52,
                gravaM3 = 0.80,
                aguaLitros = 190.0
            )

            3500 -> DosificacionData(
                psi = 3500,
                cementoKg = 400.0,
                arenaM3 = 0.50,
                gravaM3 = 0.78,
                aguaLitros = 195.0
            )

            else -> throw IllegalArgumentException("PSI no soportado")
        }
    }
}