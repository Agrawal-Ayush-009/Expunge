plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.expunge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.expunge"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Jetpack Compose dependencies
    implementation("androidx.compose.ui:ui:1.0.0") // Replace with your version
    implementation("androidx.compose.material:material:1.3.0") // Replace with your version
    implementation("androidx.compose.ui:ui-tooling-preview:1.0.0") // Replace with your version
    implementation("androidx.compose.foundation:foundation:1.0.0") // Replace with your version
    implementation("androidx.compose.ui:ui-graphics:1.0.0") // Replace with your version

    // Glide dependencies
    implementation("com.github.bumptech.glide:glide:4.12.0") // Replace with your version
    kapt("com.github.bumptech.glide:compiler:4.12.0") // Use kapt for annotation processing in Kotlin

    // Material3 dependency
    implementation("androidx.compose.material3:material3:1.0.0-alpha02") // Replace with your version

    // Additional Compose UI dependency (version 1.4.0)
    implementation("androidx.ui:ui-1.4.0") // Replace with your version if needed


    implementation("androidx.navigation:navigation-compose:2.8.0")

    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")


    // Jetpack Compose ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // For Kotlin Coroutines support with ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // For image picking and camera access
    implementation ("androidx.activity:activity-ktx:1.8.0")
    implementation ("androidx.camera:camera-core:1.2.0")
    implementation ("androidx.camera:camera-camera2:1.2.0")
    implementation ("androidx.camera:camera-lifecycle:1.2.0")
    implementation ("androidx.camera:camera-view:1.2.0")
}