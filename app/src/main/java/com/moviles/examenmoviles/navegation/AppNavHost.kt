package com.moviles.examenmoviles.navegation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moviles.examenmoviles.ui.screens.SpaceListScreen
import com.moviles.examenmoviles.ui.screens.SpaceDetailScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.SPACE_LIST,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(AppDestinations.SPACE_LIST) {
            SpaceListScreen(
                onSpaceClick = { spaceId ->
                    navController.navigate("space_detail/$spaceId")
                }
            )
        }

        composable(AppDestinations.SPACE_DETAIL) { backStackEntry ->
            val spaceId = backStackEntry.arguments?.getString("spaceId") ?: ""
            SpaceDetailScreen(
                spaceId = spaceId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}