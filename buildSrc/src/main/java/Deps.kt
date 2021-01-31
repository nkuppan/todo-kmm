object Versions {
    const val compileSdk = 30
    const val minSdk = 21
    const val targetSdk = 30
    const val androidGradlePlugin = "4.1.2"
    const val ktlint = "0.39.0"
    const val coil = "1.1.0"
    const val insetter = "0.3.1"
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val cocoapodsext = "0.11"
    const val kermit = "0.1.8"

    object Kotlin {
        const val kotlin = "1.4.20"
        const val coroutines = "1.4.2"
    }

    object AndroidX {
        const val core = "1.5.0-alpha05"
        const val activity = "1.2.0-beta02"
        const val fragment = "1.3.0-beta02"
        const val appCompat = "1.3.0-alpha02"
        const val constraintLayout = "2.1.0-alpha1"
        const val recyclerView = "1.2.0-beta01"
        const val drawerLayout = "1.1.1"
        const val swipeRefreshLayout = "1.2.0-alpha01"
        const val cardView = "1.0.0"
        const val viewPager2 = "1.1.0-alpha01"
        const val emoji = "1.2.0-alpha01"
        const val browser = "1.3.0"
        const val vectorDrawable = "1.1.0"
        const val lifecycle = "2.3.0-beta01"
        const val room = "2.3.0-alpha03"
        const val paging = "3.0.0-alpha10"
        const val navigation = "2.3.2"
        const val work = "2.5.0-beta02"
        const val hilt = "1.0.0-alpha02"
        const val dataStore = "1.0.0-alpha05"
        const val crypto = "1.1.0-alpha01"
        const val preference = "1.1.1"

        object Test {
            const val core = "1.3.1-alpha02"
            const val jUnit = "1.1.3-alpha02"
            const val runner = "1.3.1-alpha02"
        }

        object Navigation {
            const val safeArg = "2.3.2"
        }
    }

    object Google {
        const val services = "4.2.0"
        const val material = "1.3.0-alpha04"
        const val hilt = "2.30.1-alpha"

        object OssLicenses {
            const val ossLicenses = "17.0.0"
            const val gradlePlugin = "0.10.2"
        }
    }

    object Square {
        const val okHttp = "4.9.0"
        const val retrofit = "2.9.0"
        const val sqlDelight = "1.4.3"
    }

    object Firebase {
        const val firebase = "25.12.0"

        object Crashlytics {
            const val gradlePlugin = "2.4.1"
        }
    }

    object KotlinX {
        object Coroutine {
            const val android = "1.4.2"
        }
    }
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val ktlint = "com.pinterest:ktlint:${Versions.ktlint}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val cocoapodsext = "co.touchlab:kotlinnativecocoapods:${Versions.cocoapodsext}"
    const val kermit = "co.touchlab:kermit:${Versions.kermit}"

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.kotlin}"
        const val serializationPlugin =
            "org.jetbrains.kotlin:kotlin-serialization:${Versions.Kotlin.kotlin}"
        const val gradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.kotlin}"

        object Coroutines {
            const val core =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutines}"
            const val android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"
        }
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}"
        const val recyclerView =
            "androidx.recyclerview:recyclerview:${Versions.AndroidX.recyclerView}"
        const val drawerLayout =
            "androidx.drawerlayout:drawerlayout:${Versions.AndroidX.drawerLayout}"
        const val swipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX.swipeRefreshLayout}"
        const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.AndroidX.viewPager2}"
        const val cardView = "androidx.cardview:cardview:${Versions.AndroidX.cardView}"
        const val emoji = "androidx.emoji:emoji:${Versions.AndroidX.emoji}"
        const val browser = "androidx.browser:browser:${Versions.AndroidX.browser}"
        const val vectorDrawable =
            "androidx.vectordrawable:vectordrawable:${Versions.AndroidX.vectorDrawable}"

        const val cryptoPreference =  "androidx.security:security-crypto:${Versions.AndroidX.crypto}"
        const val preference =  "androidx.preference:preference:${Versions.AndroidX.preference}"

        object Core {
            const val ktx = "androidx.core:core-ktx:${Versions.AndroidX.core}"
        }

        object Activity {
            const val ktx = "androidx.activity:activity-ktx:${Versions.AndroidX.activity}"
        }

        object Fragment {
            const val ktx = "androidx.fragment:fragment-ktx:${Versions.AndroidX.fragment}"
        }

        object Lifecycle {
            const val viewModelKtx =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"
            const val liveDataKtx =
                "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:${Versions.AndroidX.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.AndroidX.room}"
            const val ktx = "androidx.room:room-ktx:${Versions.AndroidX.room}"
        }

        object Paging {
            const val runtime = "androidx.paging:paging-runtime:${Versions.AndroidX.paging}"
        }

        object Navigation {
            const val uiKtx =
                "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.navigation}"
            const val fragmentKtx =
                "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}"
            const val safeArgsGradlePlugin =
                "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.AndroidX.navigation}"
        }

        object Work {
            const val runtimeKtx = "androidx.work:work-runtime-ktx:${Versions.AndroidX.work}"
        }

        object Hilt {
            const val compiler = "androidx.hilt:hilt-compiler:${Versions.AndroidX.hilt}"
            const val work = "androidx.hilt:hilt-work:${Versions.AndroidX.hilt}"

            object Lifecycle {
                const val viewModel =
                    "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.AndroidX.hilt}"
            }
        }

        object DataStore {
            const val preferences =
                "androidx.datastore:datastore-preferences:${Versions.AndroidX.dataStore}"
        }

        object Test {
            const val runner = "androidx.test:runner:${Versions.AndroidX.Test.runner}"
            const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            object Core {
                const val ktx = "androidx.test:core-ktx:${Versions.AndroidX.Test.core}"
            }

            object JUnit {
                const val ktx = "androidx.test.ext:junit-ktx:${Versions.AndroidX.Test.jUnit}"
            }
        }
    }

    object Google {
        const val servicesPlugin = "com.google.gms:google-services:${Versions.Google.services}"
        const val material = "com.google.android.material:material:${Versions.Google.material}"

        object Hilt {
            const val compiler = "com.google.dagger:hilt-compiler:${Versions.Google.hilt}"
            const val android = "com.google.dagger:hilt-android:${Versions.Google.hilt}"
            const val androidGradlePlugin =
                "com.google.dagger:hilt-android-gradle-plugin:${Versions.Google.hilt}"
        }

        object OssLicenses {
            const val ossLicenses =
                "com.google.android.gms:play-services-oss-licenses:${Versions.Google.OssLicenses.ossLicenses}"
            const val gradlePlugin =
                "com.google.android.gms:oss-licenses-plugin:${Versions.Google.OssLicenses.gradlePlugin}"
        }
    }

    object Square {
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.Square.retrofit}"

        object Retrofit {
            const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.Square.retrofit}"
            const val gsonConverter =
                "com.squareup.retrofit2:converter-gson:${Versions.Square.retrofit}"
        }

        object SqlDelight {
            const val gradlePlugin =
                "com.squareup.sqldelight:gradle-plugin:${Versions.Square.sqlDelight}"
            const val android =
                "com.squareup.sqldelight:android-driver:${Versions.Square.sqlDelight}"
            const val native =
                "com.squareup.sqldelight:native-driver:${Versions.Square.sqlDelight}"
            const val runtime =
                "com.squareup.sqldelight:runtime:${Versions.Square.sqlDelight}"
        }
    }

    object Insetter {
        const val ktx = "dev.chrisbanes:insetter-ktx:${Versions.insetter}"
    }

    object Firebase {
        const val firebase = "com.google.firebase:firebase-bom:${Versions.Firebase.firebase}"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"

        object Crashlytics {
            const val gradlePlugin =
                "com.google.firebase:firebase-crashlytics-gradle:${Versions.Firebase.Crashlytics.gradlePlugin}"
        }
    }

    object KotlinX {

        object Coroutine {
            const val android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KotlinX.Coroutine.android}"
        }
    }
}
