package com.victor.constructorpro.domain.usecase

import com.victor.constructorpro.domain.model.ConcreteCalculation

class CalculateConcreteVolumeUseCase {
    fun execute(largo: Double, ancho: Double, alto: Double): ConcreteCalculation {
        val volume = largo * ancho * alto
        return ConcreteCalculation(volume)
    }
}