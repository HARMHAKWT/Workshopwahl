package com.example.workshopwahl.data

import kotlinx.coroutines.delay   // wartet, ohne die App einzufrieren

/**
 * Das Repository beschafft Daten. Der Rest der App weiß nicht, woher sie kommen.
 * Hier ist es eine SIMULATION ohne echten Server, nur zum Üben.
 */
class AuthRepository {

    // suspend: Diese Funktion darf warten (ein Server braucht Zeit zum Antworten).
    // Aufgerufen werden darf sie nur aus einer Coroutine, z. B. mit viewModelScope.launch.
    suspend fun login(email: String, password: String): Boolean {
        delay(800)   // simuliert die Wartezeit eines Servers (0,8 Sekunden)

        // Nur zum Üben: ein fest eingetragenes Testkonto, KEINE echte Sicherheit!
        // Später würde hier ein Aufruf an einen Server stehen.
        return email.equals("test@hak.at", ignoreCase = true) && password == "1234"
    }
}