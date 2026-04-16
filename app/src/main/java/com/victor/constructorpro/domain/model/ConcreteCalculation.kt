package com.victor.constructorpro.domain.model

data class ConcreteCalculation(
    val volume: Double,
    val unit: String = "m³"
) {
    val formattedVolume: String
        get() = String.format("%.2f", volume)
}
