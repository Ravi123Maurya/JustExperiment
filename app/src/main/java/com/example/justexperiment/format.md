# Follow this format to create new screen/content

* Firstly, Create ScreenRoute [Route] and Include in [NavigationGraph]
```kotlin
    @Serializable
    data class ExampleScreen(
      val contentId: Int
    ) : Route
```


* Right above the screen composable, create screen [Content] data
  For example: 
```kotlin
    val example = Content(
        875346,
        "Example",
        "This is example description",
        Icons.Default.Category,
        route = Route.ExampleScreen(875346)
  )
```

* Each screen should have @param `id: Int` and `navController: NavController`
```kotlin
    @Composable
    fun ExampleScreen(
        id: Int,
        navController: NavController
    )
```

* Every screens must be wrapped in [ContentView] Layout.

* Look at [AnimationScreen] example below:

```kotlin
val animation = Content(
  875346,
  "Animations",
  "Cool color, size, and shape animations",
  Icons.Default.Category,
  route = Route.AnimationScreen(875346)
)
@Composable
fun AnimationScreen(
  id: Int,
  navController: NavController
) {

  ContentView(
    id = id,
    onNavBack = { navController.popBackStack() }
  ) {
    AnimationScreenContent()
  }

}

@Composable
private fun AnimationScreenContent() {

  Column(
    modifier = Modifier
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {

    // These are private screen content composables
    JustAnimatedVisibility()
    JustAnimateColor()
    JustAnimateSize()
    JustCrossfade()
    JustMarkdown()
    JustInfiniteRotation()

  }

}

```

