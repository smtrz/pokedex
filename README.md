# PokeDex Android App

<p align="center">
This app demonstrates Android development with Hilt, Coroutines, Flow, Jetpack (compose, ViewModel,paging 3), and Material Design based on MVVM architecture.
</p>

<p align="center">
<img src="https://i.imgur.com/uWvmeuD.png"/>
</p>

## Functionality
This app shows pokemon evolution :
 - Currently i have assumed that each pokemon evolves to only once.

## Download
Go to the [Google Drive Link](https://bit.ly/3c7PqGu) to download the debug APK.

## Architecture
App is based on the MVVM architecture and the Repository pattern.

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- Jetpack
  - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - Paging3  - Manages seemless data loading and cache data in viewmodel scope
  - Navigation - For screen navigation and data passing
  - Compose   - For designing the composable UI

## Open API
App is using the [PokeAPI](https://pokeapi.co/) for constructing RESTful API.<br>
PokeAPI provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Pokémon.

## Note and Improvements
- Adding UI testing using Espresso.
