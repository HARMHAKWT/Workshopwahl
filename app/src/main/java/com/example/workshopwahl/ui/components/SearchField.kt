package com.example.workshopwahl.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workshopwahl.R
import com.example.workshopwahl.ui.theme.SearchFill
import com.example.workshopwahl.ui.theme.TextBlack
import com.example.workshopwahl.ui.theme.TextGrey

/**
 * Suchfeld "Workshop suchen" mit dem Standard-Textfeld von Material 3.
 * Aussehen laut Mockup: 276 dp breit, graue Füllung, Rand, Radius 8, Lupe links.
 */
@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Schrift 15 sp wie im Mockup, für Eingabe und Platzhalter gleich
    val textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp, lineHeight = 17.sp)

    OutlinedTextField(
        value = value,                          // der aktuelle Suchtext
        onValueChange = onValueChange,          // wird bei jedem Tippen aufgerufen
        modifier = modifier.width(276.dp),
        singleLine = true,                      // nur eine Zeile
        textStyle = textStyle,

        // Platzhalter, solange das Feld leer ist
        placeholder = { Text(text = "Workshop suchen", style = textStyle) },

        // Lupe am Anfang des Feldes (dekorativ, der Platzhalter erklärt das Feld)
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },

        shape = RoundedCornerShape(8.dp),       // Eckenradius aus dem Mockup

        // Die Tastatur zeigt statt "Enter" eine Such-Taste
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),

        // Farben aus dem Mockup. focused = Feld ist angetippt, unfocused = Ruhezustand.
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SearchFill,
            unfocusedContainerColor = SearchFill,
            focusedBorderColor = TextBlack,
            unfocusedBorderColor = TextGrey,
            focusedTextColor = TextBlack,
            unfocusedTextColor = TextBlack,
            focusedPlaceholderColor = TextBlack,
            unfocusedPlaceholderColor = TextBlack,
            focusedLeadingIconColor = TextBlack,
            unfocusedLeadingIconColor = TextBlack,
            cursorColor = TextBlack
        )
    )
}