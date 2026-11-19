package com.example.workshopwahl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workshopwahl.ui.theme.TextBlack

@Composable
fun ChipButton(
    text: String,
    fill: Color,                              // Füllfarbe
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 98.dp,                        // Breite laut Mockup
    borderColor: Color = TextBlack,
    textSize: TextUnit = 15.sp,
    enabled: Boolean = true
) {
    val shape = RoundedCornerShape(8.dp)      // Eckenradius aus dem Mockup
    Box(
        modifier = modifier
            .width(width)
            .height(48.dp)                    // Höhe laut Mockup
            .clip(shape)                      // schneidet Füllung und Klickfläche rund ab
            .background(fill)
            .border(1.dp, borderColor, shape)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = TextBlack,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = textSize,
                lineHeight = textSize * 1.15f   // 115 % Zeilenhöhe wie im Mockup
            )
        )
    }
}