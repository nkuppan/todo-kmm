buildscript {
    val kotlin_version by extra("1.4.21")
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }

    dependencies {
        //Below plugins are common gradle plugin to support android
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.Kotlin.gradlePlugin)

        //Below plugins are common gradle plugin to support android
        classpath(Libs.Google.servicesPlugin)
        classpath(Libs.Google.OssLicenses.gradlePlugin)
        classpath(Libs.Firebase.Crashlytics.gradlePlugin)
        classpath(Libs.AndroidX.Navigation.safeArgsGradlePlugin)

        //This below deps are added to support kotlin multi-platform
        classpath(Libs.Kotlin.serializationPlugin)
        classpath(Libs.Square.SqlDelight.gradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}