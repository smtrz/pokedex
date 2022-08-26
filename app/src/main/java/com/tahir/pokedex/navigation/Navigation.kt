package com.tahir.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tahir.pokedex.ui.PokeMonList
import com.tahir.pokedex.ui.ProfileScreen

/**
 * This file holds the functions for navigation of the application using navigation component
 */
@Composable
fun Navigation() {
	val navController = rememberNavController()
	// setting up the NavHost with route to both of the screens.
	NavHost(navController = navController, startDestination = Screen.PokemonListScreen.route) {
		// PokemonList Screen route.
		composable(route = Screen.PokemonListScreen.route) {
			PokeMonList(navController)

		}
		/* Pokemon details screen route, we expect a id to be recieved from the previous screen, we will be passing
		 if from the selected pokemon in the form of path variable */
		composable(route = Screen.PokemonDetailscreen.route + "/{id}", listOf(navArgument("id") {
			type = NavType.IntType
			defaultValue = 0
			nullable = false

		})) { entry ->
			// getting the id form the path and setting up the pokemon profile screen
			var id = entry.arguments?.getInt("id")
			ProfileScreen(id!!, navController)

		}
	}


}

