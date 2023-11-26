## Pokedex
This application represents the final project of the Pazarama Android Bootcamp, developed as an individual endeavor. It involves the listing and detailed information of Pokémon using data obtained from the [OMDb Api](https://pokeapi.co/).



## Technologies and Libraries
- [Android Architecture Components](https://developer.android.com/topic/architecture) - Collection
  of libraries that help you design robust, testable, and maintainable apps.
    - A single-activity architecture, using
      the [Navigation](https://developer.android.com/guide/navigation) to manage composable
      transactions.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an
      action when lifecycle state change
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
      UI-related data that isn’t destroyed on UI changes.
    - [UseCases](https://developer.android.com/topic/architecture/domain-layer) - Located domain
      layer
      that sits between the UI layer and the data layer.
    - [Repository](https://developer.android.com/topic/architecture/data-layer) - Located in data
      layer that contains application data and business logic
- [Glide](https://github.com/bumptech/glide)
- [Kotlin](https://kotlinlang.org/)
  based [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) [Flow](https://developer.android.com/kotlin/flow)
  for asynchronous.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging
  network data.
- [Navigation](https://developer.android.com/guide/navigation) - Manage transaction among the
  fragments
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency
  Injection Library
- Testing
    - [ Flow Turbine](https://github.com/cashapp/turbine) - Turbine is a small testing library for
      kotlinx.coroutines Flow.
    - [Truth](https://truth.dev/) - A library for performing assertions in tests

## Demo
https://github.com/muhammedmercan/PazaramaFinalPokedexApp/assets/58571212/9e424c1e-99f9-4ec3-9877-f574a7cd7bd0



## Screenshots
| Splash Screen | Main Screen | Details Screen | 
| --------------|-------------| ---------------| 
![Splash Screen](https://raw.githubusercontent.com/muhammedmercan/PazaramaFinalPokedexApp/master/screenshots/splash_screen.png)  | ![Main Screen](https://raw.githubusercontent.com/muhammedmercan/PazaramaFinalPokedexApp/master/screenshots/home_page.png) | ![Details Screen](https://raw.githubusercontent.com/muhammedmercan/PazaramaFinalPokedexApp/master/screenshots/detail_page.png) | 

| Search With Number | Search With Text |
| ------------------ | ---------------- |
![Search With Number](https://raw.githubusercontent.com/muhammedmercan/PazaramaFinalPokedexApp/master/screenshots/search_with_number.png) | ![Search With Text](https://raw.githubusercontent.com/muhammedmercan/PazaramaFinalPokedexApp/master/screenshots/search_with_text.png) 
