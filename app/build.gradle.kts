plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.shahzadian.freenic"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.shahzadian.freenic"
        minSdk = 27
        targetSdk = 36
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
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":feature-welcome-screen"))
    implementation(project(":feature-data-fill-screen"))
    implementation(project(":data-user"))
    implementation(project(":feature-dashboard-screen"))
    implementation(project(":feature-main-screen"))
    implementation(project(":feature-cravings-screen"))
    implementation(project(":feature-breathe-exercise-screen"))
    implementation(project(":feature-distraction-activity-screen"))
    implementation(project(":feature-profile-screen"))
    implementation(project(":feature-subscription"))
    implementation(project(":feature-goals-screen"))
    implementation(project(":feature-achievements"))
    implementation(project(":feature-insights-screen"))
    implementation(project(":feature-recovery-calculator"))
    implementation(project(":feature-debug-panel-screen"))
    implementation(project(":feature-debug-params"))
    implementation(project(":feature-savings-goal"))
    implementation(project(":feature-savings-goal-screen"))
    implementation(project(":feature-widget"))
    implementation(project(":feature-ad"))
    implementation(project(":feature-motivational-notes"))
    implementation(project(":feature-motivational-notes-screen"))
    implementation(project(":feature-slip-screen"))
    implementation(project(":feature-slip"))
    implementation(project(":feature-journey-calendar-screen"))

//    Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    
    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}