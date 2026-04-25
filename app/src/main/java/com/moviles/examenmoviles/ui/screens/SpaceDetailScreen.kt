package com.moviles.examenmoviles.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moviles.examenmoviles.data.CoworkingSpace
import com.moviles.examenmoviles.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpaceDetailScreen(
    spaceId: String,
    onBackClick: () -> Unit = {},
    space: CoworkingSpace? = mockSpaces.find { it.id == spaceId }
) {
    val selectedTab = remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalles del Espacio", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab.intValue,
                onTabSelected = { selectedTab.intValue = it }
            )
        }
    ) { padding ->
        if (space != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(space.name, style = MaterialTheme.typography.headlineMedium)
                Text(space.location, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))

                Text("Descripción", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
                Text(space.description, style = MaterialTheme.typography.bodyMedium)

                Text("Capacidad", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
                Text("${space.capacity} personas", style = MaterialTheme.typography.bodyMedium)

                Text("Precio por Hora", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
                Text("$${space.pricePerHour}", style = MaterialTheme.typography.bodyMedium)

                Text("Disponibilidad", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
                Text(if (space.isAvailable) "Disponible" else "No disponible", style = MaterialTheme.typography.bodyMedium)

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    enabled = space.isAvailable
                ) {
                    Text("Reservar Espacio")
                }
            }
        } else {
            Text("Espacio no encontrado", modifier = Modifier.padding(16.dp))
        }
    }
}

private val mockSpaces = listOf(
    CoworkingSpace(
        id = "1",
        name = "Hub Tecnológico Centro",
        description = "Espacio de coworking moderno en el corazón de la ciudad con internet de alta velocidad",
        imageUrl = "",
        location = "Calle Principal 123",
        capacity = 50,
        pricePerHour = 10.0,
        isAvailable = true
    ),
    CoworkingSpace(
        id = "2",
        name = "Espacio Creativo",
        description = "Perfecto para diseñadores y creativos con iluminación natural",
        imageUrl = "",
        location = "Avenida del Arte 456",
        capacity = 30,
        pricePerHour = 8.0,
        isAvailable = true
    ),
    CoworkingSpace(
        id = "3",
        name = "Centro Empresarial",
        description = "Ambiente profesional para reuniones de negocios con salas de conferencias",
        imageUrl = "",
        location = "Bulevar Corporativo 789",
        capacity = 100,
        pricePerHour = 15.0,
        isAvailable = false
    ),
    CoworkingSpace(
        id = "4",
        name = "Lab de Startups",
        description = "Espacio dedicado a startups y emprendedores con mentoría",
        imageUrl = "",
        location = "Camino de Innovación 321",
        capacity = 25,
        pricePerHour = 12.0,
        isAvailable = true
    )
)

