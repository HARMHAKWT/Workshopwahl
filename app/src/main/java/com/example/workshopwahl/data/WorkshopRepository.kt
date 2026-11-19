package com.example.workshopwahl.data

import kotlinx.coroutines.delay

/**
 * Beschafft die Workshops. Hier eine SIMULATION mit festen Daten.
 * Später würde ein Aufruf an einen Server dastehen.
 */
class WorkshopRepository {

    suspend fun getWorkshops(): List<Workshop> {

        return listOf(
            Workshop("1", "Kochen", "Mo, 29. Juni 2027", "08.00 Uhr", "Raum 204"),
            Workshop("2", "Fotografie", "Di, 30. Juni 2027", "08.00 Uhr", "Raum 204")
        )
    }

    // Meldet den Nutzer für einen Workshop an. true = hat geklappt.
    suspend fun register(workshopId: String): Boolean {

        return true
    }
}