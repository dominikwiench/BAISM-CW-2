package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ConverterScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ConverterScreen(modifier: Modifier = Modifier) {
    var input by rememberSaveable { mutableStateOf("") }
    var selectedName by rememberSaveable {
        mutableStateOf(Conversion.METERS_TO_FEET.name)
    }
    var result by rememberSaveable { mutableStateOf<String?>(null) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    val selected = Conversion.valueOf(selectedName)

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Konwerter jednostek",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Wybierz przelicznik")

        Column(modifier = Modifier.selectableGroup()) {
            Conversion.entries.forEach { conversion ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .selectable(
                            selected = selected == conversion,
                            onClick = {
                                selectedName = conversion.name
                                result = null
                                error = null
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == conversion,
                        onClick = null
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(conversion.label)
                }
            }
        }

        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                result = null
                error = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Przeliczana wartość (${selected.sourceUnit})") },
            singleLine = true,
            isError = error != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )

        error?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                result = null
                error = null

                val value = input
                    .trim()
                    .replace(',', '.')
                    .toDoubleOrNull()

                if (value == null) {
                    error = "Wpisz poprawną liczbę"
                } else {
                    try {
                        val converted = UnitConverter.convert(
                            value = value,
                            conversion = selected
                        )

                        result = String.format(
                            Locale.getDefault(),
                            "%.3f %s",
                            converted,
                            selected.targetUnit
                        )
                    } catch (exception: IllegalArgumentException) {
                        error = exception.message
                            ?: "Nie można przeliczyć tej wartości"
                    }
                }
            }
        ) {
            Text("Przelicz")
        }

        result?.let { converted ->
            Text(
                text = "Wynik: $converted",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterPreview() {
    UnitConverterTheme {
        ConverterScreen()
    }
}