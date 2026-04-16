package com.victor.constructorpro.domain.usecase

import com.victor.constructorpro.domain.model.VarillaData

class GetTablaVarillasUseCase {

    fun execute(): List<VarillaData> {
        return listOf(
            VarillaData("#2", "1/4\"", 6.35, 0.25, 12.0, 3.00),
            VarillaData("#3", "3/8\"", 9.52, 0.56, 12.0, 6.72),
            VarillaData("#4", "1/2\"", 12.70, 0.99, 12.0, 11.88),
            VarillaData("#5", "5/8\"", 15.88, 1.55, 12.0, 18.60),
            VarillaData("#6", "3/4\"", 19.05, 2.24, 12.0, 26.88),
            VarillaData("#7", "7/8\"", 22.22, 3.04, 12.0, 36.48),
            VarillaData("#8", "1\"", 25.40, 3.97, 12.0, 47.64),
            VarillaData("#9", "1 1/8\"", 28.65, 5.06, 12.0, 60.72),
            VarillaData("#10", "1 1/4\"", 32.26, 6.40, 12.0, 76.80),
            VarillaData("#12", "1 1/2\"", 38.10, 8.94, 12.0, 107.28)
        )
    }
}