# Introduction
This is a library that includes some commonly used convenience methods for Android development.


## Shared Preference Made Easy

`PreferenceUtil.kt` contains convenience methods for accessing `SharedPreference` in Android. 

Usage for saving/getting `Boolean`:

```kotlin
val prefs: SharedPreferences = context.defaultPrefs()
// saving
prefs.save(KEY, false)

// getting
val defaultValue = true
prefs.get(KEY, defaultValue)
```

Usage for saving/getting `String`:

```kotlin
val prefs: SharedPreferences = context.defaultPrefs()
// saving
prefs.save(KEY, "doggy")

// getting
val defaultValue = "no pets yet"
prefs.get(KEY, defaultValue)
```

## DSL for making simple dialogs

Usage:

```kotlin
makeDialog {
    messageTitle = "Confirmation"
    messageText = "Are you sure to logout?"
    positiveText = "OK"
    positiveOnClickListener = { dialog ->
        // implement positive button logic
        dialog.dismiss()
    }
    negativeText = "Cancel"
    negativeOnClickListener = { dialog ->
        // implement negative button logic
        dialog.dismiss()
    }
}.apply { show() }
```

## RecyclerView on bottom detection (Rx)
Detecting the bottom of the list is a very common usage for apps that require pagination. 

Usage:

```kotlin
homeRecyclerView.apply {
    adapter = HomeAdapter()
    initBottomDetectListener() // <--- add this line
}
```

After adding the line above, the `recyclerView` will become Reactive, and `homeRecyclerView.onBottomDetectedObservable` will emit a `Unit` signal whenever bottom is reached.

```kotlin
homeRecyclerView.onBottomDetectedObservable
   .flatMap { repo.loadMore() }
   .observeOn(repo.getMainThread())
   .subscribe {
       // do stuff
   }
```

This method also comes with simple locking mechanism. The bottom signal will be emitted only one time as soon as it becomes visible.
It will not emit another signal, until it goes out of visibility and becomes visible again.

## Int.dpTopx() / Int.pxToDp
Converting between `dp` and `px` is a pretty common usage in Android development.

Usage:

```kotlin
10.dpToPx() <--- this is 10 dp in pixels
30.pxToDp() <--- this is 30 pixels in dp
```


