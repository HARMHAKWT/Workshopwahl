package com.example.workshopwahl.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator   // der Fortschrittskreis
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect                // führt Code aus, wenn sich ein Wert ändert
import androidx.compose.runtime.collectAsState                // macht aus dem Zustand einen Compose-Zustand
import androidx.compose.runtime.getValue                      // nötig für das Wort "by"
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel        // holt das ViewModel
import com.example.workshopwahl.R
import com.example.workshopwahl.ui.components.LoginField
import com.example.workshopwahl.ui.theme.ActionBlue
import com.example.workshopwahl.ui.theme.AppTheme
import com.example.workshopwahl.ui.theme.PlaceholderGrey
import com.example.workshopwahl.ui.theme.TextBlack

/**
 * Der Login-Screen mit Anbindung an das ViewModel.
 * onLoginSuccess wird aufgerufen, wenn die Anmeldung geklappt hat
 * (die Navigation entscheidet dann, wohin es geht).
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()       // erzeugt oder findet das ViewModel automatisch
) {
    // Beobachtet den Zustand aus dem ViewModel. Ändert er sich, wird der Screen neu gezeichnet.
    val uiState by viewModel.uiState.collectAsState()

    // LaunchedEffect läuft jedes Mal, wenn sich isLoggedIn ändert.
    // Ist die Anmeldung geklappt: Navigation auslösen und dem ViewModel Bescheid geben.
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onLoginSuccess()
            viewModel.onLoginHandled()
        }
    }

    // Der eigentliche Screen. viewModel::onEmailChange ist eine Kurzschreibweise für
    // "rufe die Funktion onEmailChange des ViewModels auf".
    LoginContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::onLoginClick,
        modifier = modifier
    )
}

/**
 * Zeichnet den Login-Screen nach dem Figma-Mockup (412 x 917 dp).
 * Diese Funktion kennt kein ViewModel und keine Navigation.
 * Sie bekommt den Zustand und die Funktionen von außen. Das macht sie
 * wiederverwendbar und in der Vorschau einfach zu zeigen.
 */
@Composable
fun LoginContent(
    uiState: LoginUiState,                    // was angezeigt werden soll
    onEmailChange: (String) -> Unit,          // Tippen im E-Mail-Feld
    onPasswordChange: (String) -> Unit,       // Tippen im Passwortfeld
    onLoginClick: () -> Unit,                 // Klick auf Anmelden
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)                       // weißer Hintergrund wie im Mockup
            .padding(top = 64.dp, bottom = 135.dp),        // Abstände oben und unten laut Mockup
        horizontalAlignment = Alignment.CenterHorizontally // alles mittig
    ) {
        // Logo: 168 x 168 dp
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "youngBiz HAK und digBiz HAK Waidhofen an der Thaya",
            modifier = Modifier.size(168.dp)
        )

        Spacer(Modifier.height(30.dp))

        Text(
            text = "Workshopwahl",
            style = MaterialTheme.typography.headlineSmall,
            color = TextBlack
        )

        Spacer(Modifier.height(51.dp))

        LoginField(
            value = uiState.email,
            onValueChange = onEmailChange,
            placeholder = "E-Mail",
            isError = uiState.emailError        // roter Rahmen, wenn der Zustand es verlangt
        )

        Spacer(Modifier.height(26.dp))

        LoginField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            placeholder = "Passwort",
            isPassword = true,
            isError = uiState.passwordError
        )

        // Platz für die Fehlermeldung (49 dp wie im Mockup). Der Platz bleibt auch ohne
        // Meldung reserviert, damit nichts hin- und herspringt.
        Box(
            modifier = Modifier.width(306.dp).height(49.dp),
            contentAlignment = Alignment.Center
        ) {
            // let läuft nur, wenn errorMessage nicht null ist
            uiState.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                )
            }
        }

        Button(
            onClick = onLoginClick,                // die Prüfung passiert im ViewModel
            enabled = !uiState.isLoading,          // während des Ladens nicht erneut klickbar
            modifier = Modifier.width(306.dp).height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ActionBlue,
                contentColor = TextBlack,
                // Auch im deaktivierten Zustand gleich aussehen, damit der Button nicht ausgraut
                disabledContainerColor = ActionBlue,
                disabledContentColor = TextBlack
            ),
            border = BorderStroke(1.dp, TextBlack)
        ) {
            if (uiState.isLoading) {
                // Ladezustand: Fortschrittskreis statt Text
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = TextBlack
                )
            } else {
                Text(
                    text = "Anmelden",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp,
                        lineHeight = 20.sp
                    )
                )
            }
        }

        // Schiebt den Hinweistext ganz nach unten
        Spacer(Modifier.weight(1f))

        Text(
            text = "Nur für Schüler:innen und Lehrpersonen der HAK Waidhofen/Thaya",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            ),
            color = PlaceholderGrey,
            modifier = Modifier.padding(horizontal = 21.dp)
        )
    }
}

// ---------- Vorschauen: je ein Zustand aus dem Mockup ----------

@Preview(name = "Normal", showBackground = true, widthDp = 412, heightDp = 917)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginContent(
            uiState = LoginUiState(),               // Startzustand
            onEmailChange = {}, onPasswordChange = {}, onLoginClick = {}
        )
    }
}

@Preview(name = "Fehler", showBackground = true, widthDp = 412, heightDp = 917)
@Composable
fun LoginScreenErrorPreview() {
    AppTheme {
        LoginContent(
            uiState = LoginUiState(
                errorMessage = "Bitte E-Mail-Adresse und Passwort eingeben.",
                emailError = true,
                passwordError = true
            ),
            onEmailChange = {}, onPasswordChange = {}, onLoginClick = {}
        )
    }
}

@Preview(name = "Lädt", showBackground = true, widthDp = 412, heightDp = 917)
@Composable
fun LoginScreenLoadingPreview() {
    AppTheme {
        LoginContent(
            uiState = LoginUiState(email = "test@hak.at", password = "1234", isLoading = true),
            onEmailChange = {}, onPasswordChange = {}, onLoginClick = {}
        )
    }
}