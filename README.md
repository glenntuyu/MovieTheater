# MovieTheater
## MovieTheater App using Kotlin

## Features
- Show list of `Now Showing` from Top Rated Movies with Paging
- Show detail of movie that selected from home page
- Unit Test for ViewModel

## Tech stack & Open-source libraries
- Paging 3 - for infinite scrolling.
- Room - Access app's SQLite database with in-app objects and compile-time checks.
- ViewModel - UI related data holder, lifecycle aware.
- Lifecycles - Create a UI that automatically responds to lifecycle events.
- LiveData - Build data objects that notify views when the underlying database changes.
- Dagger 2 - for dependency injection.
- Kotlin Coroutines - for managing background threads with simplified code and reducing needs for callbacks
- Flow - for managing data behind viewModel (use case, repository, etc)
- Glide - for image loading
- Retrofit2 & OkHttp3 - to make REST requests to the web service integrated.
- Unit Test - for unit testing view model

## Open API
MovieTheater uses the themoviedb for constructing RESTful API. ("https://api.themoviedb.org/")

## Preview
<img src="/previews/topRatedMovies.PNG" width="50%" />
<img src="/previews/movieDetail.PNG" width="50%"/>
