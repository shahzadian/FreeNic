// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}

// Enforce a consistent kotlin-metadata-jvm across all modules to avoid Hilt/Kotlin metadata version conflicts
subprojects {
    configurations.all {
        resolutionStrategy {
            // Force the kotlin-metadata-jvm version to match the Kotlin compiler metadata (fixes Hilt metadata version error)
            force("org.jetbrains.kotlin:kotlin-metadata-jvm:2.3.0")
        }
    }
}