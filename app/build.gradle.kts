plugins {
    id(Plugins.androidApplication)
    kotlin(KotlinPlugins.android)
    kotlin(KotlinPlugins.kapt)
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id("kotlin-android-extensions")
    id(Plugins.hilt)
}

dependencies {
    implementation(AndroidX.appCompat)

    implementation(AndroidX.appCompat)
   // implementation(Compose.composeAnimation)
    //implementation(Compose.composeAnimationCore)
    implementation(Compose.runtime)
    implementation(Compose.runtimeLiveData)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.uiTooling)
    implementation(Compose.foundation)
    implementation(Compose.compiler)
    implementation(Compose.constraintLayout)
    implementation(Compose.activity)
    implementation(Compose.navigation)
    implementation(Compose.materialIcons)
    implementation(Compose.materialIconsExtended)

    implementation(Glide.glide)
    kapt(Glide.glideCompiler)

    implementation(Google.material)

    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltNavigation)
    implementation(Hilt.hiltNavigationCompose)
    implementation(Hilt.hiltViewModel)
    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltCompilerKapt)

    implementation(Kotlinx.datetime)

    implementation(Jetpack.navigationFragment)
    implementation(Jetpack.navigationUi)

    implementation(Ktor.android)


    implementation(SquareUp.retrofit)
    implementation(SquareUp.gson)
    debugImplementation(SquareUp.leakCanary)


}

android {
    compileSdk = Application.compileSdk
    defaultConfig {
        applicationId = Application.appId
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        versionCode = Application.versionCode
        versionName = Application.versionName
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
}

