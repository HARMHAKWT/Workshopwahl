package com.example.workshopwahl.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workshopwahl.ui.screens.LoginScreen
import com.example.workshopwahl.ui.screens.WorkshopListScreen

@Composable
fun AppNavigation() {
    // Die Fernbedienung der Navigation, nur einmal ganz oben anlegen
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                // Wird vom Screen aufgerufen, wenn die Anmeldung geklappt hat.
                // Die Navigation weiß nichts von Passwörtern. Sie entscheidet nur, wohin es geht.
                onLoginSuccess = {
                    // nach dem Login zur Workshopliste, Login aus dem Verlauf entfernen
                    navController.navigate("workshops") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("workshops") {
            WorkshopListScreen(
                onDetailsClick = { id -> navController.navigate("workshop/$id") },
                onRegisteredWorkshopsClick = { navController.navigate("registered") }
            )
        }

        // Platzhalter, bis die Screens aus dem Mockup gebaut sind
        composable("workshop/{id}") { entry ->
            val id = entry.arguments?.getString("id")
            Text("Details zu Workshop $id folgen")
        }
        composable("registered") {
            Text("Hier kommen später deine angemeldeten Workshops")
        }
    }
}
