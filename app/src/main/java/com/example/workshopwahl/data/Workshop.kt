package com.example.workshopwahl.data

/** Beschreibt einen Workshop, so wie ihn eine Karte im Mockup zeigt. */
data class Workshop(
    val id: String,       // eindeutige Nummer, wird für die Navigation gebraucht
    val title: String,    // z. B. "Kochen"
    val date: String,     // z. B. "Mo, 29. Juni 2027"
    val time: String,     // z. B. "08.00 Uhr"
    val room: String      // z. B. "Raum 204"
)