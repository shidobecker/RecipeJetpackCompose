object Hilt {
    const val version = "2.37"
    const val hiltAndroid = "com.google.dagger:hilt-android:$version"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$version"

    private const val hiltNavigationVersion = "1.0.0-alpha03"
    const val hiltNavigation = "androidx.hilt:hilt-navigation:$hiltNavigationVersion"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltNavigationVersion"

    private const val hiltJetpack = "1.0.0-alpha01"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltJetpack"

    // When using Kotlin.
    const val hiltCompilerKapt = "androidx.hilt:hilt-compiler:$hiltJetpack"
}