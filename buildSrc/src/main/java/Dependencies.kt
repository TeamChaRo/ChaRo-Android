object KotlinDependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
}

object AndroidXDependencies {
    const val coreKtx = "androidx.core:core-ktx:${Versions.core_ktx_version}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout_version}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt_version}"
    const val dataStore = "androidx.datastore:datastore-preferences:${Versions.datastore_version}"
    const val dataStoreCore =
        "androidx.datastore:datastore-preferences-core:${Versions.datastore_version}"
    const val viewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_ktx_version}"
    const val activityKtx =
        "androidx.activity:activity-ktx:${Versions.activity_ktx}"
    const val fragmentKtx =
        "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_version}"
    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.navigation_version}"
    const val security = "androidx.security:security-crypto:${Versions.security_version}"
    const val biometric = "androidx.biometric:biometric:${Versions.biometric_version}"
    const val kotlinxSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinx_serialization_version}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardview_version}"

}

object TestDependencies {
    const val jUnit = "junit:junit:${Versions.junit_version}"
    const val androidTest = "androidx.test.ext:junit:${Versions.android_test_version}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso_version}"
}

object ClassPathDependencies {
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}"
}

object MaterialDesignDependencies {
    const val materialDesign =
        "com.google.android.material:material:${Versions.material_design_version}"
}

object KaptDependencies {
    const val hilt = "com.google.dagger:hilt-compiler:${Versions.hilt_version}"
    const val glideCompiler =
        "com.github.bumptech.glide:compiler:${Versions.glide_compiler_version}"
    const val dagger = "com.google.dagger:dagger-compiler:${Versions.dagger_version}"
    const val daggerAndroidCompiler =
        "com.google.dagger:dagger-android-processor:${Versions.dagger_version}"
}

object ThirdPartyDependencies {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger_version}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger_version}"
    const val daggerAndroidSupport =
        "com.google.dagger:dagger-android-support:${Versions.dagger_version}"
    const val gson = "com.google.code.gson:gson:${Versions.gson_version}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    const val retrofitGsonConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"
    const val okhttpBOM = "com.squareup.okhttp3:okhttp-bom:${Versions.okhttp_version}"
    const val okhttp = "com.squareup.okhttp3:okhttp"
    const val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava_version}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid_version}"
}
