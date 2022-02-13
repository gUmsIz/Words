package com.gumsiz.words.ui


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gumsiz.words.ui.detayf.DetailScreen
import com.gumsiz.words.ui.mainf.MainScreen

@Composable
fun VerbenNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ){
        composable(Screen.MainScreen.route){
            MainScreen(navController)
        }
        composable(
            Screen.DetailScreen.route + "/{word}",
            arguments = listOf(
                navArgument("word"){
                    type = NavType.StringType
                    nullable = false
                }
            )
        ){

            it.arguments?.getString("word")?.let { it1 ->
                DetailScreen(
                    it1
                )
            }
        }
    }
}
sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object DetailScreen: Screen("detail_screen")
}