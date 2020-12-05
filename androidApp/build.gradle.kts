import com.nkuppan.todo.buildsrc.Libs

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.firebase.crashlytics")
}
android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.nkuppan.todo"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation(project(":shared"))

    implementation(Libs.Google.material)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.swipeRefreshLayout)
    implementation(Libs.AndroidX.recyclerView)
    implementation(Libs.AndroidX.cardView)
    implementation(Libs.AndroidX.vectorDrawable)

    implementation(Libs.KotlinX.Coroutine.android)

    implementation(Libs.AndroidX.Lifecycle.liveDataKtx)
    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)

    implementation(Libs.AndroidX.Room.ktx)
    implementation(Libs.AndroidX.Room.runtime)
    implementation(Libs.AndroidX.Room.compiler)

    implementation(Libs.AndroidX.Navigation.uiKtx)
    implementation(Libs.AndroidX.Navigation.fragmentKtx)

    implementation(Libs.AndroidX.Core.ktx)

    implementation(platform(Libs.Firebase.firebase))
    implementation(Libs.Firebase.analytics)
    implementation(Libs.Firebase.crashlytics)
}

