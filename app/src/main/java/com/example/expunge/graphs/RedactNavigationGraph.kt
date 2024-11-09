package com.example.expunge.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expunge.screens.DocumentRedactionScreen

@Composable
fun ExpungeNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = RedactScreens.EnterDocument.route
    ){
        composable(route = RedactScreens.EnterDocument.route){
            DocumentRedactionScreen()
        }

//        composable(route = RedactScreens.FinalRedact.route){
//            FinalRedact()
//        }
//
//        composable(route = RedactScreens.RedactSetting.route){
//            RedactionSettingScreen(){
//                navController.navigate(RedactScreens.FinalRedact.route)
//            }
//        }
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