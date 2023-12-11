plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.nextsolutions.sprintsphere_pro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nextsolutions.sprintsphere_pro"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.nextsolutions.sprintsphere_pro.di.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    implementation("androidx.compose.ui:ui:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")


    implementation("com.google.accompanist:accompanist-navigation-animation:0.30.0")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.0")


    implementation("androidx.compose.material:material-icons-core:1.4.3")

    implementation("androidx.compose.material:material-icons-core:1.4.0")
    implementation("androidx.compose.material:material-icons-extended:1.4.0")


    // Compose dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha06")
    implementation("com.google.accompanist:accompanist-flowlayout:0.20.0")


    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.44")
    kapt ("com.google.dagger:hilt-android-compiler:2.44")
    implementation ("androidx.hilt:hilt-work:1.0.0")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.work:work-runtime-ktx:2.8.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")


    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.0")

    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.5.2")

    implementation("com.github.bumptech.glide:compose:1.0.0-alpha.1")



    // Pager and Indicators - Accompanist
    implementation("com.google.accompanist:accompanist-pager:0.24.2-alpha")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.24.2-alpha")


    // Local unit tests
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
    testImplementation("io.mockk:mockk:1.10.5")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.0-alpha04")

    // Instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.37")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.37")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test:core-ktx:1.4.0")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
    androidTestImplementation("io.mockk:mockk-android:1.10.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("com.linkedin.dexmaker:dexmaker:2.28.3")
    implementation("androidx.test:monitor:1.6.1")

    implementation("ovh.plrapps:mapcompose:2.0.0-rc01")

    implementation("org.osmdroid:osmdroid-android:6.1.16")

    implementation("com.google.accompanist:accompanist-permissions:0.24.13-rc")

    // Location Services
    implementation("com.google.android.gms:play-services-location:20.0.0")


}