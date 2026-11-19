package com.example.workshopwahl.ui.screens

import com.example.workshopwahl.data.Workshop

/** Der Zustand der Workshopübersicht: alles, was der Screen zum Zeichnen braucht. */
data class WorkshopListUiState(
    val isLoading: Boolean = false,                       // true, solange geladen wird
    val workshops: List<Workshop> = emptyList(),          // alle geladenen Workshops
    val query: String = "",                               // Text im Suchfeld
    val registeredIds: Set<String> = emptySet()           // IDs der Workshops, für die man angemeldet ist
) {
    // Die Liste, die wirklich angezeigt wird: bei Suchtext nur die passenden Workshops.
    val visibleWorkshops: List<Workshop>
        get() = if (query.isBlank()) workshops
        else workshops.filter { it.title.contains(query.trim(), ignoreCase = true) }
}