plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdkVersion(Apps.compileSdk)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = Apps.pacakageName
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
        versionCode(Apps.versionCode)
        versionName(Apps.versionName)

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Versions.java_version
        targetCompatibility = Versions.java_version
    }
    kotlinOptions {
        jvmTarget = Versions.jvm_version
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Hilt
    implementation(AndroidXDependencies.hilt)
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    kapt(KaptDependencies.hilt)

    // DataStore
    implementation(AndroidXDependencies.dataStore)
    implementation(AndroidXDependencies.dataStoreCore)

    // Android KTX
    implementation(AndroidXDependencies.fragmentKtx)
    implementation(AndroidXDependencies.activityKtx)
    implementation(AndroidXDependencies.viewModelKtx)

    // Glide
    implementation(ThirdPartyDependencies.glide)
    kapt(KaptDependencies.glideCompiler)

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

    // Reactive Extensions(Rx)
    implementation(ThirdPartyDependencies.rxJava)
    implementation(ThirdPartyDependencies.rxAndroid)

    // Androidx Security
    implementation(AndroidXDependencies.security)

    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.androidTest)
    androidTestImplementation(TestDependencies.espresso)

    //CardView
    implementation(AndroidXDependencies.cardview)
}
