package com.example.workshopwahl.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workshopwahl.data.WorkshopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkshopListViewModel(
    private val repository: WorkshopRepository = WorkshopRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkshopListUiState())
    val uiState: StateFlow<WorkshopListUiState> = _uiState.asStateFlow()

    // init läuft einmal, sobald das ViewModel erzeugt wird: Die Workshops werden sofort geladen.
    init {
        loadWorkshops()
    }

    private fun loadWorkshops() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val workshops = repository.getWorkshops()        // wartet auf das Repository
            _uiState.update { it.copy(isLoading = false, workshops = workshops) }
        }
    }

    // Bei jedem Tippen im Suchfeld
    fun onQueryChange(value: String) {
        _uiState.update { it.copy(query = value) }
    }

    // Klick auf "Anmelden" bei einem Workshop
    fun onRegisterClick(workshopId: String) {
        if (workshopId in _uiState.value.registeredIds) return    // schon angemeldet
        viewModelScope.launch {
            val success = repository.register(workshopId)
            if (success) {
                // + fügt die ID zur Menge hinzu (es entsteht eine neue Menge)
                _uiState.update { it.copy(registeredIds = it.registeredIds + workshopId) }
            }
        }
    }
}