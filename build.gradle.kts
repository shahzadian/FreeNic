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

            // androidx.datastore 1.2.x ships libdatastore_shared_counter.so built with NDK r20,
            // which Google Play reports as not compatible with 16 KB memory page sizes.
            // 1.1.7 ships the same native library built with NDK r25c and passes the check.
            // Pinned here so a transitive dependency cannot pull 1.2.x back in.
            eachDependency {
                if (requested.group == "androidx.datastore") {
                    useVersion("1.1.7")
                    because("1.2.x native library is not 16 KB page size compatible")
                }
            }
        }
    }
}