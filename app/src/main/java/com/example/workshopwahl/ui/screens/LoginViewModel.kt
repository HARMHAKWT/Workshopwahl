package com.example.workshopwahl.ui.screens

import androidx.lifecycle.ViewModel                // Grundklasse: überlebt z. B. das Drehen des Handys
import androidx.lifecycle.viewModelScope           // Bereich, in dem Coroutines laufen und automatisch enden
import com.example.workshopwahl.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow    // veränderbarer Zustand
import kotlinx.coroutines.flow.StateFlow           // nur lesbare Sicht darauf
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update              // Zustand sicher ändern
import kotlinx.coroutines.launch                   // startet eine Coroutine

/**
 * Das ViewModel enthält die Logik des Login-Screens.
 * Der Screen zeigt nur an, was hier im Zustand steht.
 */
class LoginViewModel(
    // Der Standardwert sorgt dafür, dass viewModel() das ViewModel ohne Zutun erzeugen kann.
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    // _uiState ist privat und veränderbar: nur das ViewModel selbst darf ihn ändern.
    private val _uiState = MutableStateFlow(LoginUiState())

    // uiState ist öffentlich und nur lesbar: der Screen darf ihn beobachten, aber nicht ändern.
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Wird bei jedem Tippen im E-Mail-Feld aufgerufen
    fun onEmailChange(value: String) {
        // update { ... } ersetzt den Zustand durch eine Kopie mit geänderten Werten.
        // "it" ist der alte Zustand. Alte Fehlermeldungen verschwinden beim Weitertippen.
        _uiState.update {
            it.copy(email = value, errorMessage = null, emailError = false, passwordError = false)
        }
    }

    // Wird bei jedem Tippen im Passwortfeld aufgerufen
    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(password = value, errorMessage = null, emailError = false, passwordError = false)
        }
    }

    // Wird beim Klick auf "Anmelden" aufgerufen
    fun onLoginClick() {
        val current = _uiState.value           // der aktuelle Zustand
        if (current.isLoading) return          // läuft schon eine Prüfung? Dann nichts tun

        // 1. Eingabe prüfen: isBlank() ist true, wenn leer oder nur Leerzeichen
        val emailMissing = current.email.isBlank()
        val passwordMissing = current.password.isBlank()
        if (emailMissing || passwordMissing) {
            _uiState.update {
                it.copy(
                    errorMessage = "Bitte E-Mail-Adresse und Passwort eingeben.",
                    emailError = emailMissing,        // nur die leeren Felder markieren
                    passwordError = passwordMissing
                )
            }
            return   // hier aufhören, kein Server-Aufruf
        }

        // 2. Ladezustand setzen: der Screen zeigt jetzt einen Fortschrittskreis
        _uiState.update {
            it.copy(isLoading = true, errorMessage = null, emailError = false, passwordError = false)
        }

        // 3. Das Repository fragen. launch startet eine Coroutine, damit die App
        //    während des Wartens bedienbar bleibt.
        viewModelScope.launch {
            val success = repository.login(current.email.trim(), current.password)

            // 4. Ergebnis in den Zustand schreiben
            _uiState.update {
                if (success) {
                    it.copy(isLoading = false, isLoggedIn = true)
                } else {
                    it.copy(
                        isLoading = false,
                        errorMessage = "E-Mail-Adresse oder Passwort ist falsch.",
                        emailError = true,
                        passwordError = true
                    )
                }
            }
        }
    }

    // Der Screen ruft das nach dem Wechsel zum nächsten Screen auf,
    // damit die Navigation nicht ein zweites Mal ausgelöst wird.
    fun onLoginHandled() {
        _uiState.update { it.copy(isLoggedIn = false) }
    }
}