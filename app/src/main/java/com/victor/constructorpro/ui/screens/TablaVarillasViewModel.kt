package com.victor.constructorpro.ui.screens

import androidx.lifecycle.ViewModel
import com.victor.constructorpro.domain.model.VarillaData
import com.victor.constructorpro.domain.usecase.GetTablaVarillasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TablaVarillasViewModel(
    private val getTablaVarillasUseCase: GetTablaVarillasUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<VarillaData>>(emptyList())
    val uiState: StateFlow<List<VarillaData>> = _uiState

    init {
        loadTabla()
    }

    private fun loadTabla() {
        _uiState.value = getTablaVarillasUseCase.execute()
    }
}