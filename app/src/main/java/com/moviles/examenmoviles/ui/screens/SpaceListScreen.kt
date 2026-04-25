package com.moviles.examenmoviles.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.moviles.examenmoviles.ui.components.CoworkingSpaceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpaceListScreen(
    modifier: Modifier = Modifier,
    spaces: List<CoworkingSpace> = mockSpaces,
    onSpaceClick: (String) -> Unit = {}
) {
    val selectedTab = remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Espacios de Coworking", style = MaterialTheme.typography.headlineMedium) },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(spaces) { space ->
                CoworkingSpaceCard(space = space, onClick = { onSpaceClick(space.id) })
            }
        }
    }
}

private val mockSpaces = listOf(
    CoworkingSpace(
        id = "1",
        name = "Hub Tecnológico Centro",
        description = "Espacio de coworking moderno en el corazón de la ciudad",
        imageUrl = "",
        location = "Calle Principal 123",
        capacity = 50,
        pricePerHour = 10.0,
        isAvailable = true
    ),
    CoworkingSpace(
        id = "2",
        name = "Espacio Creativo",
        description = "Perfecto para diseñadores y creativos",
        imageUrl = "",
        location = "Avenida del Arte 456",
        capacity = 30,
        pricePerHour = 8.0,
        isAvailable = true
    ),
    CoworkingSpace(
        id = "3",
        name = "Centro Empresarial",
        description = "Ambiente profesional para reuniones de negocios",
        imageUrl = "",
        location = "Bulevar Corporativo 789",
        capacity = 100,
        pricePerHour = 15.0,
        isAvailable = false
    ),
    CoworkingSpace(
        id = "4",
        name = "Lab de Startups",
        description = "Espacio dedicado a startups y emprendedores",
        imageUrl = "",
        location = "Camino de Innovación 321",
        capacity = 25,
        pricePerHour = 12.0,
        isAvailable = true
    )
)
