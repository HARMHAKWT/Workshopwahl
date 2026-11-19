package com.example.workshopwahl.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workshopwahl.R
import com.example.workshopwahl.data.Workshop
import com.example.workshopwahl.ui.theme.ActionBlue
import com.example.workshopwahl.ui.theme.RegisterFill
import com.example.workshopwahl.ui.theme.TextBlack
import com.example.workshopwahl.ui.theme.TextGrey

/** Eine Workshop-Karte laut Mockup: 325 x 242 dp, schwarzer Rand, Radius 12. */
@Composable
fun WorkshopCard(
    workshop: Workshop,
    isRegistered: Boolean,                // true = Button zeigt "Angemeldet"
    onDetailsClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.width(325.dp).height(242.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, TextBlack),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(top = 3.dp, bottom = 11.dp)) {
            Text(
                text = workshop.title,
                style = MaterialTheme.typography.headlineSmall,   // 24 sp wie im Mockup
                color = TextBlack,
                modifier = Modifier.padding(start = 24.dp)
            )
            Spacer(Modifier.height(27.dp))
            InfoRow(iconRes = R.drawable.ic_calendar, text = workshop.date)
            Spacer(Modifier.height(16.dp))
            InfoRow(iconRes = R.drawable.ic_schedule, text = workshop.time)
            Spacer(Modifier.height(16.dp))
            InfoRow(iconRes = R.drawable.ic_location, text = workshop.room)

            // schiebt die Buttons an den unteren Kartenrand
            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ChipButton(
                    text = "Details",
                    fill = ActionBlue,
                    width = 98.dp,
                    onClick = onDetailsClick
                )
                ChipButton(
                    text = if (isRegistered) "Angemeldet" else "Anmelden",
                    fill = RegisterFill,
                    width = 140.dp,
                    enabled = !isRegistered,         // schon angemeldet: nicht erneut klickbar
                    onClick = onRegisterClick
                )
            }
        }
    }
}

// Eine Zeile mit Icon und Text (Datum, Uhrzeit, Raum). private = nur in dieser Datei benutzbar.
@Composable
private fun InfoRow(@DrawableRes iconRes: Int, text: String) {
    Row(
        modifier = Modifier.padding(start = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,           // dekorativ, der Text daneben sagt alles
            tint = TextBlack,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(24.dp))
        Text(
            text = text,
            color = TextGrey,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp, lineHeight = 17.sp)
        )
    }
}