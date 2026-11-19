package com.example.workshopwahl.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workshopwahl.R
import com.example.workshopwahl.data.Workshop
import com.example.workshopwahl.ui.components.ChipButton
import com.example.workshopwahl.ui.components.SearchField
import com.example.workshopwahl.ui.components.WorkshopCard
import com.example.workshopwahl.ui.theme.AppBarFill
import com.example.workshopwahl.ui.theme.AppTheme
import com.example.workshopwahl.ui.theme.GreyButtonFill
import com.example.workshopwahl.ui.theme.TextBlack
import com.example.workshopwahl.ui.theme.TextGrey

/**
 * Verbindet das ViewModel mit der Oberfläche. Details und die Liste der
 * angemeldeten Workshops sind Sache der Navigation und kommen als Funktionen von außen.
 */
@Composable
fun WorkshopListScreen(
    onDetailsClick: (String) -> Unit,
    onRegisteredWorkshopsClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkshopListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    WorkshopListContent(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onDetailsClick = onDetailsClick,
        onRegisterClick = viewModel::onRegisterClick,
        onRegisteredWorkshopsClick = onRegisteredWorkshopsClick,
        modifier = modifier
    )
}

/** Zeichnet die Workshopübersicht (Frame 412 x 917 dp). Kennt kein ViewModel. */
@Composable
fun WorkshopListContent(
    uiState: WorkshopListUiState,
    onQueryChange: (String) -> Unit,
    onDetailsClick: (String) -> Unit,
    onRegisterClick: (String) -> Unit,
    onRegisteredWorkshopsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WorkshopTopBar()
        Spacer(Modifier.height(60.dp))                   // Abstand Leiste zu Suchfeld laut Mockup
        SearchField(value = uiState.query, onValueChange = onQueryChange)
        Spacer(Modifier.height(52.dp))                   // Abstand Suchfeld zur ersten Karte

        // Dieser Bereich nimmt den ganzen Platz zwischen Suchfeld und Button ein.
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(color = TextBlack)
                uiState.visibleWorkshops.isEmpty() -> Text(
                    text = "Keine Workshops gefunden.",
                    color = TextGrey,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
                )
                else -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(52.dp),    // Abstand zwischen den Karten
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // key sorgt dafür, dass Compose jede Karte eindeutig wiedererkennt
                    items(uiState.visibleWorkshops, key = { it.id }) { workshop ->
                        WorkshopCard(
                            workshop = workshop,
                            isRegistered = workshop.id in uiState.registeredIds,
                            onDetailsClick = { onDetailsClick(workshop.id) },
                            onRegisterClick = { onRegisterClick(workshop.id) }
                        )
                    }
                }
            }
        }

        ChipButton(
            text = "zu deinen angemeldeten Workshops",
            fill = GreyButtonFill,
            borderColor = TextGrey,
            width = 358.dp,
            textSize = 20.sp,
            onClick = onRegisteredWorkshopsClick,
            modifier = Modifier
                .padding(top = 32.dp)
                .navigationBarsPadding()                 // Platz für die Navigationsleiste des Handys
                .padding(bottom = 16.dp)
        )
    }
}

// Leiste oben: Menü-Symbol, Titel, Avatar (64 dp hoch, graue Füllung, Radius 8)
@Composable
private fun WorkshopTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()                         // Platz für die Statusleiste
            .height(64.dp)
            .background(AppBarFill, RoundedCornerShape(8.dp))
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Menü folgt später */ }) {
            Icon(
                painter = painterResource(R.drawable.ic_menu),
                contentDescription = "Menü",
                tint = TextBlack
            )
        }
        Text(
            text = "Workshopwahl",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.ic_avatar),
                contentDescription = "Profil",
                modifier = Modifier.size(32.dp).clip(CircleShape)
            )
        }
    }
}

// ---------- Vorschauen ----------

private val previewWorkshops = listOf(
    Workshop("1", "Kochen", "Mo, 29. Juni 2027", "08.00 Uhr", "Raum 204"),
    Workshop("2", "Fotografie", "Di, 30. Juni 2027", "08.00 Uhr", "Raum 204")
)

@Preview(name = "Liste", showBackground = true, widthDp = 412, heightDp = 917)
@Composable
fun WorkshopListPreview() {
    AppTheme {
        WorkshopListContent(
            uiState = WorkshopListUiState(workshops = previewWorkshops),
            onQueryChange = {}, onDetailsClick = {}, onRegisterClick = {}, onRegisteredWorkshopsClick = {}
        )
    }
}

@Preview(name = "Lädt", showBackground = true, widthDp = 412, heightDp = 917)
@Composable
fun WorkshopListLoadingPreview() {
    AppTheme {
        WorkshopListContent(
            uiState = WorkshopListUiState(isLoading = true),
            onQueryChange = {}, onDetailsClick = {}, onRegisterClick = {}, onRegisteredWorkshopsClick = {}
        )
    }
}

@Preview(name = "Angemeldet", showBackground = true, widthDp = 412, heightDp = 917)
@Composable
fun WorkshopListRegisteredPreview() {
    AppTheme {
        WorkshopListContent(
            uiState = WorkshopListUiState(workshops = previewWorkshops, registeredIds = setOf("1")),
            onQueryChange = {}, onDetailsClick = {}, onRegisterClick = {}, onRegisteredWorkshopsClick = {}
        )
    }
}