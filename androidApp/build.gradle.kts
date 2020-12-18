plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.firebase.crashlytics")
}
android {

    compileSdkVersion(Versions.compileSdk)

    defaultConfig {
        applicationId = "com.nkuppan.todo"
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
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

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    configurations {
        implementation.get().exclude(mapOf("group" to "org.jetbrains", "module" to "annotations"))
    }
}

dependencies {

    implementation(project(":shared"))

    implementation("com.ancient.essentials:android-essentials:1.3.0")

    implementation(Libs.Google.material)

    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.swipeRefreshLayout)
    implementation(Libs.AndroidX.recyclerView)
    implementation(Libs.AndroidX.cardView)
    implementation(Libs.AndroidX.vectorDrawable)
    implementation(Libs.AndroidX.preference)
    implementation(Libs.AndroidX.cryptoPreference)

    implementation(Libs.AndroidX.Lifecycle.liveDataKtx)
    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)

    implementation(Libs.AndroidX.Navigation.uiKtx)
    implementation(Libs.AndroidX.Navigation.fragmentKtx)

    implementation(Libs.KotlinX.Coroutine.android)

    implementation(Libs.AndroidX.Core.ktx)

    implementation(Libs.Google.OssLicenses.ossLicenses)

    implementation(platform(Libs.Firebase.firebase))
    implementation(Libs.Firebase.analytics)
    implementation(Libs.Firebase.crashlytics)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
}

