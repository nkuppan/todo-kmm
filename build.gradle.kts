buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }

    dependencies {
        //Below plugins are common gradle plugin to support android
        classpath(com.nkuppan.todo.buildsrc.Libs.androidGradlePlugin)
        classpath(com.nkuppan.todo.buildsrc.Libs.Kotlin.gradlePlugin)

        //Below plugins are common gradle plugin to support android
        classpath(com.nkuppan.todo.buildsrc.Libs.Google.servicesPlugin)
        classpath(com.nkuppan.todo.buildsrc.Libs.Google.OssLicenses.gradlePlugin)
        classpath(com.nkuppan.todo.buildsrc.Libs.Firebase.Crashlytics.gradlePlugin)
        classpath(com.nkuppan.todo.buildsrc.Libs.AndroidX.Navigation.safeArgsGradlePlugin)

        //This below deps are added to support kotlin multi-platform

        classpath(com.nkuppan.todo.buildsrc.Libs.Kotlin.serializationPlugin)
        classpath(com.nkuppan.todo.buildsrc.Libs.Square.SqlDelight.gradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}