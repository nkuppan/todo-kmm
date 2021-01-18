import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("kotlin-android-extensions")
    id("com.squareup.sqldelight")
}

kotlin {

    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    android()

    val ktorVersion = "1.4.0"
    val serializationVersion = "1.0.0-RC"
    val coroutinesVersion = "1.4.1"

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
        implementation("io.ktor:ktor-client-core:$ktorVersion")
        implementation("io.ktor:ktor-client-serialization:$ktorVersion")
        implementation(Libs.Square.SqlDelight.runtime)
    }

    sourceSets["androidMain"].dependencies {
        implementation(Libs.Google.material)
        implementation("io.ktor:ktor-client-android:$ktorVersion")
        implementation(Libs.Square.SqlDelight.android)
    }

    sourceSets["iosMain"].dependencies {
        implementation("io.ktor:ktor-client-ios:$ktorVersion")
        implementation(Libs.Square.SqlDelight.native)
    }
}
android {
    compileSdkVersion(Versions.compileSdk)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Versions.versionCode
        versionName = Versions.versionName
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

sqldelight {
    database("MyDatabase") {
        packageName = "com.nkuppan.todo.db"
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)