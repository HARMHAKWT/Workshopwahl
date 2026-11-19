package com.example.workshopwahl.ui.screens

/**
 * Der Zustand des Login-Screens: alles, was der Screen zum Zeichnen braucht.
 * Eine data class ist ein Behälter für zusammengehörige Werte.
 * Die Vorgabewerte (= ...) sind der Startzustand.
 */
data class LoginUiState(
    val email: String = "",                 // Text im E-Mail-Feld
    val password: String = "",              // Text im Passwortfeld
    val isLoading: Boolean = false,         // true, solange die Prüfung läuft
    val errorMessage: String? = null,       // Fehlertext, null = keine Meldung
    val emailError: Boolean = false,        // E-Mail-Feld rot markieren?
    val passwordError: Boolean = false,     // Passwortfeld rot markieren?
    val isLoggedIn: Boolean = false         // true, wenn die Anmeldung geklappt hat
)