plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
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

    implementation(com.nkuppan.todo.buildsrc.Libs.Google.material)
    implementation(com.nkuppan.todo.buildsrc.Libs.AndroidX.appCompat)
    implementation(com.nkuppan.todo.buildsrc.Libs.AndroidX.constraintLayout)
    implementation(com.nkuppan.todo.buildsrc.Libs.AndroidX.swipeRefreshLayout)
    implementation(com.nkuppan.todo.buildsrc.Libs.AndroidX.recyclerView)
    implementation(com.nkuppan.todo.buildsrc.Libs.AndroidX.cardView)
    implementation(com.nkuppan.todo.buildsrc.Libs.AndroidX.Core.ktx)

    implementation(com.nkuppan.todo.buildsrc.Libs.KotlinX.Coroutine.android)

    implementation(com.nkuppan.todo.buildsrc.Libs.AndroidX.Navigation.uiKtx)
    implementation(com.nkuppan.todo.buildsrc.Libs.AndroidX.Navigation.fragmentKtx)
    implementation(com.nkuppan.todo.buildsrc.Libs.AndroidX.Navigation.fragmentKtx)
    implementation(com.nkuppan.todo.buildsrc.Libs.AndroidX.Core.ktx)
}

