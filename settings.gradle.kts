pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FreeNic"
include(":app")
include(":common")
include(":feature-home-screen")
include(":feature-data-fill")
include(":data-user")
include(":feature-dashboard")
