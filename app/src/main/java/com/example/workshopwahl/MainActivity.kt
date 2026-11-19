package com.example.workshopwahl

import android.os.Bundle
import androidx.activity.ComponentActivity               // Grundlage einer Activity mit Compose
import androidx.activity.compose.setContent              // setzt die Compose-Oberfläche ein
import androidx.activity.enableEdgeToEdge                // Inhalt reicht bis unter Status- und Navigationsleiste
import com.example.workshopwahl.navigation.AppNavigation
import com.example.workshopwahl.ui.theme.AppTheme

/**
 * Startpunkt der App. Android startet diese Activity zuerst
 * (festgelegt in der Datei AndroidManifest.xml).
 */
class MainActivity : ComponentActivity() {

    // onCreate läuft einmal, wenn die Activity erzeugt wird
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)   // Pflichtaufruf der Oberklasse
        enableEdgeToEdge()                   // optional, bei Fehlern diese Zeile samt Import löschen

        // setContent legt fest, was auf dem Bildschirm erscheint
        setContent {
            // AppTheme gibt allen Screens deine Farben und Schriften (aus Theme.kt)
            AppTheme {
                // AppNavigation zeigt je nach Route den passenden Screen.
                // Die App beginnt mit "login".
                AppNavigation()
            }
        }
    }
}