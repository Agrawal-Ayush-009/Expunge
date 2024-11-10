package com.example.expunge.graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expunge.SharedViewmodel
import com.example.expunge.screens.DocumentExpungeScreen
import com.example.expunge.screens.FinalRedact

@Composable
fun ExpungeNavigationGraph(navController: NavHostController) {
    val sharedViewmodel: SharedViewmodel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = RedactScreens.EnterDocument.route
    ) {
        composable(route = RedactScreens.EnterDocument.route) {
            DocumentExpungeScreen(sharedViewmodel = sharedViewmodel){
                navController.navigate(route = RedactScreens.FinalRedact.route)
            }
        }

        composable(route = RedactScreens.FinalRedact.route) {
            FinalRedact(sharedViewmodel,
                onBackclick = {
                    navController.popBackStack()
                })
        }
    }
}

sealed class RedactScreens(
    val route: String,
) {
    data object EnterDocument : RedactScreens(
        route = "enter_doc",
    )

    data object FinalRedact : RedactScreens(
        route = "final_redact",
    )

    data object RedactSetting : RedactScreens(
        route = "redact_setting",
    )
}