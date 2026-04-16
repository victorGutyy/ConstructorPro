package com.victor.constructorpro.domain.usecase

import com.victor.constructorpro.domain.model.DosificacionObraData

class GetDosificacionObraByPsiUseCase {

    fun execute(psi: Int): DosificacionObraData {
        return when (psi) {
            2000 -> DosificacionObraData(
                psi = 2000,
                cementoBultos = 1.0,
                cementoPaladas = 12.5,
                arenaPaladas = 35.0,
                gravaPaladas = 45.0,
                aguaBaldes = 6.0
            )

            2500 -> DosificacionObraData(
                psi = 2500,
                cementoBultos = 1.0,
                cementoPaladas = 12.5,
                arenaPaladas = 30.0,
                gravaPaladas = 40.0,
                aguaBaldes = 5.0
            )

            3000 -> DosificacionObraData(
                psi = 3000,
                cementoBultos = 1.0,
                cementoPaladas = 12.5,
                arenaPaladas = 25.0,
                gravaPaladas = 35.0,
                aguaBaldes = 4.5
            )

            3500 -> DosificacionObraData(
                psi = 3500,
                cementoBultos = 1.0,
                cementoPaladas = 12.5,
                arenaPaladas = 22.0,
                gravaPaladas = 30.0,
                aguaBaldes = 4.0
            )

            else -> throw IllegalArgumentException("PSI no soportado")
        }
    }
}