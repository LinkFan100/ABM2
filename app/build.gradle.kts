plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.abm2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.abm2"
        minSdk = 26
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
//    implementation(libs.androidx.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Room Components
//    implementation("androidx.room:room-runtime:$rootProject.roomVersion")
//    annotationProcessor("androidx.room:room-compiler:$rootProject.roomVersion")
//val room_version = "2.6.1"
//    implementation(libs.androidx.room.runtime)
//    annotationProcessor(libs.room.compiler)
//    testImplementation(libs.room.testing)
}