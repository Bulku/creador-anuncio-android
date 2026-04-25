package com.leonvelez.creadoranuncioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.width

data class AdDraft(
    val format: String = "Banner",
    val brand: String = "",
    val title: String = "",
    val description: String = "",
    val cta: String = "Más información",
    val backgroundStyle: String = "Claro",
    val buttonStyle: String = "Azul"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AdCreatorApp()
            }
        }
    }
}

@Composable
fun AdCreatorApp() {
    var currentStep by remember { mutableStateOf(1) }
    var adDraft by remember { mutableStateOf(AdDraft()) }

    when (currentStep) {
        1 -> Step1FormatScreen(
            adDraft = adDraft,
            onFormatChange = { adDraft = adDraft.copy(format = it) },
            onNext = { currentStep = 2 }
        )

        2 -> Step2ContentScreen(
            adDraft = adDraft,
            onDraftChange = { adDraft = it },
            onBack = { currentStep = 1 },
            onNext = { currentStep = 3 }
        )

        3 -> Step3StyleScreen(
            adDraft = adDraft,
            onDraftChange = { adDraft = it },
            onBack = { currentStep = 2 },
            onNext = { currentStep = 4 }
        )

        4 -> Step4PreviewScreen(
            adDraft = adDraft,
            onBack = { currentStep = 3 },
            onRestart = {
                adDraft = AdDraft()
                currentStep = 1
            }
        )
    }
}

@Composable
fun Step1FormatScreen(
    adDraft: AdDraft,
    onFormatChange: (String) -> Unit,
    onNext: () -> Unit
) {
    ScreenContainer(title = "Paso 1: Elige el formato") {
        Text("Selecciona el tipo de anuncio que quieres crear.")

        Spacer(modifier = Modifier.height(16.dp))

        listOf("Banner", "Native").forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = adDraft.format == option,
                    onClick = { onFormatChange(option) }
                )
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) {
            Text("Siguiente")
        }
    }
}

@Composable
fun Step2ContentScreen(
    adDraft: AdDraft,
    onDraftChange: (AdDraft) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    ScreenContainer(title = "Paso 2: Escribe el contenido") {
        OutlinedTextField(
            value = adDraft.brand,
            onValueChange = { onDraftChange(adDraft.copy(brand = it)) },
            label = { Text("Marca o nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = adDraft.title,
            onValueChange = { onDraftChange(adDraft.copy(title = it)) },
            label = { Text("Título del anuncio") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = adDraft.description,
            onValueChange = { onDraftChange(adDraft.copy(description = it)) },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = adDraft.cta,
            onValueChange = { onDraftChange(adDraft.copy(cta = it)) },
            label = { Text("Texto del botón CTA") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onBack, modifier = Modifier.weight(1f)) {
                Text("Atrás")
            }
            Button(
                onClick = onNext,
                modifier = Modifier.weight(1f),
                enabled = adDraft.title.isNotBlank() && adDraft.description.isNotBlank()
            ) {
                Text("Siguiente")
            }
        }
    }
}

@Composable
fun Step3StyleScreen(
    adDraft: AdDraft,
    onDraftChange: (AdDraft) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    ScreenContainer(title = "Paso 3: Personaliza el estilo") {
        Text("Color de fondo")

        Spacer(modifier = Modifier.height(8.dp))

        listOf("Claro", "Oscuro", "Verde").forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = adDraft.backgroundStyle == option,
                    onClick = {
                        onDraftChange(adDraft.copy(backgroundStyle = option))
                    }
                )
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Color del botón")

        Spacer(modifier = Modifier.height(8.dp))

        listOf("Azul", "Rojo", "Negro").forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = adDraft.buttonStyle == option,
                    onClick = {
                        onDraftChange(adDraft.copy(buttonStyle = option))
                    }
                )
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onBack, modifier = Modifier.weight(1f)) {
                Text("Atrás")
            }
            Button(onClick = onNext, modifier = Modifier.weight(1f)) {
                Text("Ver vista previa")
            }
        }
    }
}

@Composable
fun Step4PreviewScreen(
    adDraft: AdDraft,
    onBack: () -> Unit,
    onRestart: () -> Unit
) {
    ScreenContainer(title = "Paso 4: Vista previa del anuncio") {
        Text(
            text = "Así se vería el anuncio creado por el usuario:",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        AdPreviewCard(adDraft)

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F6FB))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Resumen del anuncio",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Formato: ${adDraft.format}")
                Text("Marca: ${adDraft.brand.ifBlank { "No definida" }}")
                Text("Título: ${adDraft.title.ifBlank { "No definido" }}")
                Text("Descripción: ${adDraft.description.ifBlank { "No definida" }}")
                Text("CTA: ${adDraft.cta.ifBlank { "No definido" }}")
                Text("Fondo: ${adDraft.backgroundStyle}")
                Text("Botón: ${adDraft.buttonStyle}")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onBack, modifier = Modifier.weight(1f)) {
                Text("Atrás")
            }
            Button(onClick = onRestart, modifier = Modifier.weight(1f)) {
                Text("Crear otro")
            }
        }
    }
}

@Composable
fun AdPreviewCard(adDraft: AdDraft) {
    val backgroundColor = when (adDraft.backgroundStyle) {
        "Oscuro" -> Color(0xFF1F1F1F)
        "Verde" -> Color(0xFFE8F5E9)
        else -> Color(0xFFF8F9FA)
    }

    val textColor = if (adDraft.backgroundStyle == "Oscuro") Color.White else Color(0xFF1F1F1F)

    val buttonColor = when (adDraft.buttonStyle) {
        "Rojo" -> Color(0xFFD32F2F)
        "Negro" -> Color(0xFF212121)
        else -> Color(0xFF1976D2)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFBDBDBD)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (adDraft.brand.isBlank()) "M" else adDraft.brand.take(1).uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = if (adDraft.brand.isBlank()) "Tu marca" else adDraft.brand,
                        color = textColor,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Anuncio patrocinado",
                        color = if (adDraft.backgroundStyle == "Oscuro") Color.LightGray else Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = if (adDraft.title.isBlank()) "Título de tu anuncio" else adDraft.title,
                color = textColor,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (adDraft.description.isBlank())
                    "Aquí aparecerá la descripción de tu anuncio para mostrar el mensaje al usuario."
                else
                    adDraft.description,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = if (adDraft.backgroundStyle == "Oscuro") Color(0xFF2C2C2C) else Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Espacio para imagen o banner",
                    color = if (adDraft.backgroundStyle == "Oscuro") Color.LightGray else Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .background(buttonColor, RoundedCornerShape(14.dp))
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {
                Text(
                    text = if (adDraft.cta.isBlank()) "Más información" else adDraft.cta,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Formato seleccionado: ${adDraft.format}",
                color = if (adDraft.backgroundStyle == "Oscuro") Color.LightGray else Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ScreenContainer(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        content()
    }
}