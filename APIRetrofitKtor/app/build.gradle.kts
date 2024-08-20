import com.android.build.api.dsl.Packaging
import com.android.utils.TraceUtils.simpleId

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlinx-serialization")
}

android {
    packagingOptions {
        resources.excludes.apply {
            add("META-INF/LICENSE")
            add("META-INF/*.properties")
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
    namespace = "com.example.apiretrofitktor"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.apiretrofitktor"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.glide)
    annotationProcessor(libs.glide.annotations)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // ktor dependencies
    implementation(libs.ktor.cio)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor)
    implementation(libs.slf4j)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.okHttp)
    implementation(libs.ktor.contentNegotiation)
}