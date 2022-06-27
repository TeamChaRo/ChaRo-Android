import org.jetbrains.kotlin.gradle.plugin.statistics.ReportStatisticsToElasticSearch.url

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}")
        classpath(ClassPathDependencies.hilt)
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.4")
        //opensource
        classpath("com.google.gms:google-services:4.3.10")

        // Add the Crashlytics Gradle plugin
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.9.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        maven ("https://devrepo.kakao.com/nexus/content/groups/public/")
        maven("https://maven.google.com")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}