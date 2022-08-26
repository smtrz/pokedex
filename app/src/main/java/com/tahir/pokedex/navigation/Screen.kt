package com.tahir.pokedex.navigation

/*
Sealed class with the variants of all the app screens.
 */
sealed class Screen(val route: String) {
	object PokemonListScreen : Screen("pokemonlist_screen")
	object PokemonDetailscreen : Screen("pokemondetail_screen")

	/**
	 * appends the function argument that needs to be passed in the navigation route.
	 * @param args
	 */
	fun withArgs(vararg args: String): String {
		return buildString {
			append(route)
			args.forEach { arg ->
				append("/$arg")
			}
		}

	}
}
