package com.example.workshopwahl.ui.components

// --- Importe: holen Bausteine aus den Bibliotheken ---
import androidx.compose.foundation.border                 // zeichnet den Rahmen um das Feld
import androidx.compose.foundation.layout.Box             // Behälter, in dem Elemente übereinander liegen
import androidx.compose.foundation.layout.fillMaxSize     // "fülle den ganzen Platz"
import androidx.compose.foundation.layout.height          // feste Höhe
import androidx.compose.foundation.layout.width           // feste Breite
import androidx.compose.foundation.shape.RoundedCornerShape // abgerundete Ecken
import androidx.compose.foundation.text.BasicTextField    // einfaches Eingabefeld ohne Material-Design
import androidx.compose.foundation.text.KeyboardOptions   // legt die Tastaturart fest
import androidx.compose.material3.MaterialTheme           // Zugriff auf Farben und Schriften des Themes
import androidx.compose.material3.Text                    // zeigt Text an
import androidx.compose.runtime.Composable                // markiert Funktionen, die Oberfläche beschreiben
import androidx.compose.ui.Alignment                      // Ausrichtung (z. B. Mitte)
import androidx.compose.ui.Modifier                       // Kette von Einstellungen für ein Element
import androidx.compose.ui.text.input.KeyboardType        // E-Mail- oder Passwort-Tastatur
import androidx.compose.ui.text.input.PasswordVisualTransformation // zeigt Punkte statt Zeichen
import androidx.compose.ui.text.input.VisualTransformation // "keine Veränderung" der Anzeige
import androidx.compose.ui.text.style.TextAlign           // Textausrichtung (zentriert)
import androidx.compose.ui.unit.dp                        // Einheit für Größen und Abstände
import androidx.compose.ui.unit.sp                        // Einheit für Schriftgrößen
import com.example.workshopwahl.ui.theme.PlaceholderGrey  // Grau aus dem Mockup (steht in Color.kt)
import com.example.workshopwahl.ui.theme.TextBlack        // Schwarz aus dem Mockup (steht in Color.kt)

/**
 * Ein Eingabefeld, das wie im Figma-Mockup aussieht:
 * 276 x 48 dp, 1 dp schwarzer Rand, Ecken mit 8 dp Radius, Text in der Mitte.
 *
 * Das Feld speichert den Text nicht selbst. Der Text kommt von außen
 * (value) und jede Änderung wird nach außen gemeldet (onValueChange).
 */
@Composable
fun LoginField(
    value: String,                          // der aktuell angezeigte Text
    onValueChange: (String) -> Unit,        // Funktion, die bei jeder Eingabe aufgerufen wird
    placeholder: String,                    // grauer Hilfetext, solange das Feld leer ist
    modifier: Modifier = Modifier,          // erlaubt dem Aufrufer, zusätzliche Einstellungen mitzugeben
    isPassword: Boolean = false,            // true = Eingabe als Punkte anzeigen
    isError: Boolean = false                // true = roter, dickerer Rahmen
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,                  // nur eine Zeile, Enter macht keinen Umbruch

        // Schrift: Textstil "labelLarge" aus dem Theme, auf 16 sp vergrößert (wie im Mockup)
        textStyle = MaterialTheme.typography.labelLarge.copy(
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = TextBlack,
            textAlign = TextAlign.Center    // Text steht mittig im Feld
        ),

        // Passwort: Zeichen als Punkte anzeigen, sonst normal
        visualTransformation =
            if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,

        // Tastatur: Passwort-Tastatur bzw. E-Mail-Tastatur (mit @-Taste)
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Email
        ),

        // Größe aus dem Mockup (Dev Mode): 276 dp breit, 48 dp hoch
        modifier = modifier.width(276.dp).height(48.dp),

        // decorationBox zeichnet das "Drumherum" des Feldes: Rahmen und Platzhalter.
        // innerTextField ist das eigentliche Eingabefeld, das wir an die richtige Stelle setzen.
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        // bei Fehler: 2 dp dicker, roter Rahmen aus dem Theme
                        width = if (isError) 2.dp else 1.dp,
                        color = if (isError) MaterialTheme.colorScheme.error else TextBlack,
                        shape = RoundedCornerShape(8.dp)   // Eckenradius aus dem Mockup
                    ),
                contentAlignment = Alignment.Center        // alles in der Mitte des Feldes
            ) {
                // Platzhalter nur zeigen, solange nichts eingegeben wurde
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp,
                            lineHeight = 20.sp
                        ),
                        color = PlaceholderGrey
                    )
                }
                innerTextField()   // das echte Eingabefeld (zeigt den getippten Text)
            }
        }
    )
}