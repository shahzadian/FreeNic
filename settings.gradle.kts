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
include(":feature-welcome-screen")
include(":feature-data-fill-screen")
include(":data-user")
include(":feature-dashboard")
include(":feature-main-screen")
