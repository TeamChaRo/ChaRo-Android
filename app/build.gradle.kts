plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    compileSdk = Apps.compileSdk
}

android {
    compileSdk = Apps.compileSdk
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = Apps.pacakageName
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        versionCode = Apps.versionCode
        versionName = Apps.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = true //프로가드 활성화
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro", "proguard-debug.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("String", "BUILD_TYPE", "\"debug\"")
            buildConfigField("boolean", "DEBUG", "true")
        }
        getByName("release") {
            isMinifyEnabled = true //프로가드 활성화
            isShrinkResources = true //안 쓰는 리소스 삭제
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            //signingConfig = signingConfigs.getByName("release")
            buildConfigField("String", "BUILD_TYPE", "\"release\"")
            buildConfigField("boolean", "DEBUG", "false")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}


dependencies {
    implementation(KotlinDependencies.kotlin)
    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.coreKtx)
    implementation(MaterialDesignDependencies.materialDesign)
    implementation(AndroidXDependencies.constraintLayout)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
//lottie
    implementation("com.airbnb.android:lottie:3.4.2")

//viewpager indicator
    implementation("com.tbuonomo:dotsindicator:4.2")

//viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

// Hilt
    implementation(AndroidXDependencies.hilt)
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation(files("libs/com.skt.Tmap_1.67.jar"))
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.google.firebase:firebase-common-ktx:20.1.0")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.3")

//    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging:22.0.0")
    implementation("com.google.firebase:firebase-messaging-ktx:23.0.0")
    implementation("com.google.firebase:firebase-analytics-ktx:20.1.0")
    implementation("com.google.firebase:firebase-auth:21.0.3")
    implementation("com.firebaseui:firebase-ui-auth:4.3.2")
    implementation("com.google.firebase:firebase-inappmessaging-display-ktx:20.1.2")

    implementation(platform("com.google.firebase:firebase-bom:29.3.0"))
    implementation("com.google.firebase:firebase-dynamic-links-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    kapt(KaptDependencies.hilt)

// DataStore
    implementation(AndroidXDependencies.dataStore)
    implementation(AndroidXDependencies.dataStoreCore)

    implementation("org.jetbrains:annotations:16.0.1")
    implementation("androidx.annotation:annotation:1.3.0")

// Android KTX
    implementation(AndroidXDependencies.fragmentKtx)
    implementation(AndroidXDependencies.activityKtx)
    implementation(AndroidXDependencies.viewModelKtx)
    implementation(AndroidXDependencies.liveDataKtx)

// Glide
    implementation(ThirdPartyDependencies.glide)
    kapt(KaptDependencies.glideCompiler)
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.11.0")
// Navigation
    implementation(AndroidXDependencies.navigation)
    implementation(AndroidXDependencies.navigationFragment)

// Gson
    implementation(ThirdPartyDependencies.gson)

// Okhttp
    implementation(platform(ThirdPartyDependencies.okhttpBOM))
    implementation(ThirdPartyDependencies.okhttp)
    implementation(ThirdPartyDependencies.okhttpInterceptor)

// Retrofit
    implementation(ThirdPartyDependencies.retrofit)
    implementation(ThirdPartyDependencies.retrofitGsonConverter)

//Retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:retrofit-converters:2.4.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

//Gson
    implementation("com.google.code.gson:gson:2.8.6")

// Reactive Extensions(Rx)
    implementation(ThirdPartyDependencies.rxJava)
    implementation(ThirdPartyDependencies.rxAndroid)

// Androidx Security
    implementation(AndroidXDependencies.security)

    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.androidTest)
    androidTestImplementation(TestDependencies.espresso)

//coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

//CardView
    implementation(AndroidXDependencies.cardview)

//recyclerview
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")


//koin
    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("io.insert-koin:koin-android:3.1.2")
    implementation("io.insert-koin:koin-android-compat:3.1.2")
    testImplementation("io.insert-koin:koin-test:3.1.2")

//kakao
    implementation("com.kakao.sdk:v2-user:2.8.2")

//google
    implementation("com.google.android.gms:play-services-auth:20.2.0")

    // Timber
    implementation ("com.jakewharton.timber:timber:4.7.1")

//opensource
    implementation("com.google.android.gms:play-services-oss-licenses:17.0.0")
}
