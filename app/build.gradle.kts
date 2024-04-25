plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.kapt")

}

android {
    namespace = "com.truck.weighbridge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.truck.weighbridge"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        dataBinding { enable = true }

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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.material)

    //Dagger
    api(libs.dagger)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.runner)
    kapt (libs.dagger.compiler)

    //Dagger Android
    api(libs.dagger.android)
    api(libs.dagger.android.support)
    kapt(libs.dagger.android.processor)

    //ViewModel and LiveData
    implementation(libs.lifecycle.extensions)

    //Kotlin
    implementation(libs.kotlinx.coroutines.core)

    //Coroutines LiveData Scope
    implementation(libs.lifecycle.livedata.ktx)

    //RecyclerView
    implementation(libs.recyclerview)

    //Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Play Services
    implementation(libs.play.services.auth)

    //Firebase Authentication
    implementation(libs.firebase.auth)
    //Analytics
    implementation(libs.firebase.analytics)
    //Firebase Database
    implementation(libs.firebase.database)

    //Room Runtime
    //Room Runtime
    implementation(libs.room.runtime)
    //Room KTX
    implementation(libs.room.ktx)
    //Room Compiler
    kapt(libs.room.compiler)

    //Lottie Animation
    implementation(libs.lottie)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.junit.ext)

}
